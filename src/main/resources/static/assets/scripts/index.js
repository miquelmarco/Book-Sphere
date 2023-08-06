const { createApp } = Vue;
const app = createApp({
    data() {
        return {
            authors: [],
            books: [],
            bookHorror: [],
            events: [],
            clients: null,
            email: "",
            password: "",
            emailRegister: "",
            passwordRegister: "",
            passwordConfirm: "",
            firstNameRegister: "",
            lastNameRegister: "",
            categoryHorror: "",
            //otros
            searchInput2: '',
            books: [],
            filter: [],
            selectedBook: null,
            categories: [],
            authors: [],
            discounts: [],
            cart: [],
        }
    },
    created() {
        this.loadData();
        this.loadCartFromLocalStorage()
    },
    methods: {
        loadData() {
            axios.get("http://localhost:8080/api/authors")
                .then(response => {
                    this.authors = response.data.slice(1, 2).concat(response.data[3])
                })
                .catch(error => console.log(error));
            axios.get("http://localhost:8080/api/books")
                .then(response => {
                    this.books = response.data;
                    this.bookHorror = this.books.filter(book => book.category === 'Horror').slice(5, 10)
                    console.log(this.bookHorror);
                    this.categoryHorror = this.bookHorror[0].category
                    console.log(this.categoryHorror)
                })
                .catch(error => console.log(error));
            axios.get("http://localhost:8080/api/events")
                .then(response => {
                    this.events = response.data.slice(1, 3).concat(response.data[4])
                })
                .catch(error => console.log(error));
            axios.get('http://localhost:8080/api/clients/current')
                .then(response => {
                    this.clients = response.data;
                    console.log(this.clients)
                })
                .catch(error => console.log(error));
        },
        logIn() {
            if (this.email && this.password) {
                if (this.email.includes("admin")) {
                    axios.post(
                        "http://localhost:8080/api/login",
                        `email=${this.email}&password=${this.password}`,
                        { headers: { 'content-type': 'application/x-www-form-urlencoded' } }
                    )
                        .then(res => {
                            if (res.status == 200) {
                                Swal.fire({
                                    position: 'center',
                                    icon: 'success',
                                    title: 'Welcome!',
                                    showConfirmButton: false,
                                    timer: 1500
                                });
                                setTimeout(() => {
                                    window.location.href = "http://localhost:8080/manager.html";
                                }, 1800);
                            }
                        })
                        .catch(err => {
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Incorrect, try again!!',
                                showConfirmButton: false,
                                timer: 1500
                            });
                        });
                } else {
                    axios.post("http://localhost:8080/api/login", `email=${this.email}&password=${this.password}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                        .then(response => {
                            Swal.fire({
                                position: 'center',
                                icon: 'success',
                                title: 'Welcome!',
                                showConfirmButton: false,
                                timer: 1000
                            });
                            setTimeout(() => {
                                window.location.reload();
                            }, 1800);
                        })
                        .catch(err => {
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Incorrect, try again!!',
                                showConfirmButton: false,
                                timer: 1500
                            });
                        });
                }
            }
        },
        register() {
            if (
                this.emailRegister &&
                this.passwordRegister &&
                this.passwordConfirm &&
                this.firstNameRegister &&
                this.lastNameRegister
            ) {
                if (this.passwordRegister === this.passwordConfirm) {
                    axios
                        .post(
                            'http://localhost:8080/api/clients/register',
                            `firstName=${this.firstNameRegister}&lastName=${this.lastNameRegister}&email=${this.emailRegister}&password=${this.passwordRegister}`,
                            { headers: { 'content-type': 'application/x-www-form-urlencoded' } }
                        )
                        .then(response => {
                            axios
                                .post(
                                    'http://localhost:8080/api/login',
                                    `email=${this.emailRegister}&password=${this.passwordRegister}`,
                                    { headers: { 'content-type': 'application/x-www-form-urlencoded' } }
                                )
                                .then(response => {
                                    Swal.fire({
                                        position: 'center',
                                        icon: 'success',
                                        title: 'Welcome!',
                                        showConfirmButton: false,
                                        timer: 1000
                                    });
                                    setTimeout(() => {
                                        window.location.reload();
                                    }, 1800);
                                })
                                .catch(err => {
                                    Swal.fire({
                                        position: 'center',
                                        icon: 'error',
                                        title: 'Incorrect, try again!!',
                                        showConfirmButton: false,
                                        timer: 1500
                                    });
                                });
                        })
                        .catch(err => {
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Incorrect, try again!!',
                                showConfirmButton: false,
                                timer: 1500
                            });
                        });
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Passwords do not match!',
                        text: 'Please make sure the passwords match.',
                        showConfirmButton: true,
                    });
                }
            }
        },
        logOut() {
            axios.post(`http://localhost:8080/api/logout`)
                .then(response => {
                    return window.location.href = "http://localhost:8080/index.html";
                })
                .catch(error => console.log(error))
        },
        discountCalculator(price, discount) {
            this.pricediscount = (discount / 100) * price
            return price - this.pricediscount
        },
        multipleItemCalculator(quantity, price) {
            return quantity * price
        },
        addToCart(book) {
            const existingBook = this.cart.find(item => item.id === book.id)
            if (existingBook) {
                existingBook.quantity++
            } else {
                this.cart.push({ ...book, quantity: 1 })
            }
            this.saveCartToLocalStorage()
        },
        saveCartToLocalStorage() {
            localStorage.setItem('cart', JSON.stringify(this.cart))
        },
        loadCartFromLocalStorage() {
            const savedCart = localStorage.getItem('cart')
            this.cart = savedCart ? JSON.parse(savedCart) : []
        },
        clearCart() {
            this.cart = []
            this.saveCartToLocalStorage()
        },
        deleteItem(title) {
            let index = this.cart.findIndex(item => item.title === title)
            if (index !== -1) {
                if (this.cart[index].quantity > 1) {
                    this.cart[index].quantity -= 1
                } else {
                    this.cart.splice(index, 1)
                }
                this.saveCartToLocalStorage()
            }
        },
        bubbleCounter() {
            return totalQuantity = this.cart.reduce((acc, book) => acc + book.quantity, 0)
        },
        goToCart() {
            window.location.href = "./pages/cart.html"
        },
    },
    computed: {
        filteredBooks2() {
            this.filter = this.books.filter(book =>
                book.title.toLowerCase().includes(this.searchInput2.toLowerCase()) &&
                (this.checkedAuthors.length === 0 || this.checkedAuthors.includes(book.author)) &&
                (this.checkedCategories.length === 0 || this.checkedCategories.includes(book.category)) &&
                (this.checkedDiscounts.length === 0 || this.checkedDiscounts.includes(book.discount))
            );
        },
        cartTotal() {
            return this.cart.reduce((total, book) => total + (book.price * book.quantity), 0)
        },
    }
})
app.mount("#app")