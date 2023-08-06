const {createApp} = Vue;

const app = createApp ({
    data(){
        return{
            clients:null,
            email: "",
            password: "",
            emailRegister: "",
            passwordRegister: "",
            passwordConfirm: "",
            firstNameRegister: "",
            lastNameRegister: "",
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
    created(){
        this.loadData();
        this.loadCartFromLocalStorage()
    },
    methods: { 
        loadData(){
            axios.get ('http://localhost:8080/api/clients/current')
            .then ( response => {
                this.clients=response.data;
                console.log(this.clients)

            })
            .catch(error => console.log(error));
        },
        logIn() {
            if (this.email && this.password) {
                if ( this.email.includes("admin")) {
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
                    axios.post("http://localhost:8080/api/login", `email=${this.email}&password=${this.password}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' }})
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
                // console.log(this.emailRegister);
                // console.log(this.passwordRegister);
                // console.log(this.passwordConfirm);
                // console.log(this.firstNameRegister);
                // console.log(this.lastNameRegister);
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
       
     goToReservation(){ //boton que direcciona
        const url ="./room-reservation.html";
            location.href=url;
    },
     logOut(){
        axios.post(`http://localhost:8080/api/logout`)
            .then(response => {
                return window.location.href = "http://localhost:8080/index.html";
            })
            .catch(error => console.log(error));
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
    }
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
}
}).mount('#app')



