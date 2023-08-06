let { createApp } = Vue;

createApp({
    data() {
        return {
            searchInput2: '',
            books: [],
            filter: [],
            selectedBook: null,
            categories: [],
            authors: [],
            discounts: [],
            cart: [],
            checkedAuthors: [],
            checkedDiscounts: [],
            checkedCategories: [],
            clients: null,
            email: "",
            password: "",
            emailRegister: "",
            passwordRegister: "",
            passwordConfirm: "",
            firstNameRegister: "",
            lastNameRegister: "",
            items: [], // Aquí deberías tener tu array original con todos los elementos
            pageSize: 10, // Cantidad de elementos por página
            currentPage: 1,
        }
    },
    created() {
        this.getBooks()
        this.loadCartFromLocalStorage()
        this.loadData()
    },
    methods: {
        getBooks() {
            axios.get("http://localhost:8080/api/books")
                .then(res => {
                    this.books = res.data
                    this.getCategories = this.books.map(book => book.category)
                    this.categories = [... new Set(this.getCategories)]
                    this.getAuthors = this.books.map(book => book.author)
                    this.authors = [... new Set(this.getAuthors)].sort((a, b) => a.localeCompare(b));
                    this.getDiscounts = this.books.map(book => book.discount)
                    this.setDiscounts = [... new Set(this.getDiscounts)].sort((a, b) => a - b)
                    this.discounts = this.setDiscounts.filter(discount => discount !== 0)
                    console.log(this.books)
                    console.log(this.discounts)
                    console.log(this.authors)
                }).catch(err => {
                    console.log(err)
                })
        },
        loadData() {
            axios.get('http://localhost:8080/api/clients/current')
                .then(response => {
                    this.clients = response.data;
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
                                    window.location.reload();
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
                                        window.location.href = 'http://localhost:8080/pages/user.html';
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
                .catch(error => console.log(error));
        },
        showModal(book) {
            this.selectedBook = book;
            this.showModalElement();
            document.addEventListener("mousemove", this.updateModalPosition);
        },
        closeModal() {
            this.selectedBook = null;
            this.hideModalElement();
            document.removeEventListener("mousemove", this.updateModalPosition);
        },
        showModalElement() {
            document.getElementById("bookModal").style.display = "block";
        },
        hideModalElement() {
            document.getElementById("bookModal").style.display = "none";
        },
        updateModalPosition(event) {
            const modal = document.getElementById("bookModal");
            const mouseX = event.clientX;
            const mouseY = event.clientY;
            modal.style.top = mouseY + "px";
            modal.style.left = mouseX + 30 + "px";
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
            this.cart = [];
            this.saveCartToLocalStorage()
        },
        deleteItem(title) {
            let index = this.cart.findIndex(item => item.title === title);
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
            window.location.href = "./cart.html"
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
            return this.cart.reduce((total, book) => total + (book.price * book.quantity), 0);
        },
    },
    
}).mount("#app")

