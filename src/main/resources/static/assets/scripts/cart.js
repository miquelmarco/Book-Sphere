let { createApp } = Vue
createApp({
    data() {
        return {
            cart: [],
            clients: null,
            email: "",
            password: "",
            emailRegister: "",
            passwordRegister: "",
            passwordConfirm: "",
            firstNameRegister: "",
            lastNameRegister: "",
        }
    },
    created() {
        this.loadCartFromLocalStorage()
        this.loadData()
    },
    methods: {
        loadCartFromLocalStorage() {
            const savedCart = localStorage.getItem('cart')
            this.cart = savedCart ? JSON.parse(savedCart) : []
        },
        loadData() {
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
                                    window.location.reload()
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
                            })
                        })
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
                            }, 1800)
                        })
                        .catch(err => {
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Incorrect, try again!!',
                                showConfirmButton: false,
                                timer: 1500
                            })
                        })
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
                                        window.location.href = 'http://localhost:8080/pages/user.html'
                                    }, 1800)
                                })
                                .catch(err => {
                                    Swal.fire({
                                        position: 'center',
                                        icon: 'error',
                                        title: 'Incorrect, try again!!',
                                        showConfirmButton: false,
                                        timer: 1500
                                    })
                                })
                        })
                        .catch(err => {
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: 'Incorrect, try again!!',
                                showConfirmButton: false,
                                timer: 1500
                            })
                        })
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Passwords do not match!',
                        text: 'Please make sure the passwords match.',
                        showConfirmButton: true,
                    })
                }
            }
        },
        logOut() {
            axios.post(`http://localhost:8080/api/logout`)
                .then(response => {
                    return window.location.href = "http://localhost:8080/index.html"
                })
                .catch(error => console.log(error))
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
            return totalQuantity = this.cart.reduce((acc, item) => acc + item.quantity, 0)
        },
        discountCalculator(price, discount) {
            this.pricediscount = (discount / 100) * price
            return price - this.pricediscount
        },
        goToPay() {
            if (this.clients) {

                if (this.clients.shippingAdress !== null) {

                    window.location.href = "../payments.html";
                } else {

                    Swal.fire({
                        position: 'center',
                        icon: 'warning',
                        title: 'Add an address to shipping',
                        showConfirmButton: false,
                        timer: 2000
                    });

                    setTimeout(() => {
                        window.location.href = "./user.html";
                    }, 1500);
                }
            } else {
                Swal.fire({
                    position: 'center',
                    icon: 'warning',
                    title: 'Please log in or register to proceed to payment',
                    showConfirmButton: false,
                    timer: 2000
                });
            }
        },

        discountColorChanger(discount) {
            if (discount > 0) {
                return "redColorDiscount"
            }
        }
    },
    computed: {
        cartTotal() {
            const total = this.cart.reduce((acc, book) => {
                const bookPriceWithDiscount = book.price * (1 - book.discount / 100)
                return acc + bookPriceWithDiscount * book.quantity;
            }, 0)
            localStorage.setItem('cartTotal', total);
            return total;
        }
    }
}).mount("#app")



