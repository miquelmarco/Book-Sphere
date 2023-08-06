let { createApp } = Vue;
createApp({
    data() {
        return {
            paymentData: {
                number: '',
                cvv: null,
                amount: null,
                description: 'Retail Payment',
            },
            bookIds: [],
            cart: [],
            cartTotalFromStorage: null,
            errMsg: ''
        }
    },
    created() {
        this.loadCartFromLocalStorage()
        this.getCartTotalFromLocalStorage()
        this.paymentData.amount = this.cartTotalFromStorage
    },
    methods: {
        makePayment() {
            console.log(this.paymentData)
            axios.post("http://localhost:8070/api/cards/payments", this.paymentData, { headers: { 'content-type': 'application/json' } })
                .then(res => {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Payment Correct',
                        showConfirmButton: false,
                        timer: 1500
                    });
                    const data = {
                        bookIds: this.bookIds
                    };
                    console.log(data)
                    setTimeout(() => {
                        axios.post("http://localhost:8080/api/orderGenerator", data, { responseType: 'arraybuffer' })
                            .then(res => {
                                const blob = new Blob([res.data], { type: 'application/pdf' })
                                const url = window.URL.createObjectURL(blob)
                                const link = document.createElement('a')
                                link.href = url;
                                link.setAttribute('download', 'ticket.pdf')
                                document.body.appendChild(link)
                                link.click();
                                window.URL.revokeObjectURL(url)
                                this.clearCart()
                                setTimeout(() => {
                                    Swal.fire({
                                        position: 'center',
                                        icon: 'success',
                                        title: 'Printing ticket',
                                        showConfirmButton: false,
                                        timer: 1500
                                    });
                                    setTimeout(() => {
                                        window.location.href = "./pages/user.html"
                                    },2000)
                                }, 1500)
                            })
                            .catch(err => {
                                console.log(err);
                                Swal.fire({
                                    position: 'center',
                                    icon: 'error',
                                    title: 'Cant make payment, try again!',
                                    showConfirmButton: false,
                                    timer: 1500
                                });
                            })
                    })
                        .catch(err => {
                            console.log(err);
                            this.errMsg = err.response.data;
                            Swal.fire({
                                position: 'center',
                                icon: 'error',
                                title: this.errMsg,
                                showConfirmButton: false,
                                timer: 1500
                            });
                        });

                },2500)

        },
        loadCartFromLocalStorage() {
            const savedCart = localStorage.getItem('cart')
            this.cart = savedCart ? JSON.parse(savedCart) : []
            this.bookIds = []
            this.cart.forEach(item => {
                for (let i = 0; i < item.quantity; i++) {
                    this.bookIds.push(item.id);
                }
            })
        },
        getCartTotalFromLocalStorage() {
            const cartTotal = localStorage.getItem('cartTotal')
            if (cartTotal) {
                console.log('Total del carrito desde localStorage:', cartTotal)
                this.cartTotalFromStorage = parseFloat(cartTotal)
                console.log(this.cartTotalFromStorage)
            } else {
                console.log('No se encontró el total del carrito en localStorage.')
            }
        },
        clearCart() {
            this.cart = [];
            this.saveCartToLocalStorage()
        },
        saveCartToLocalStorage() {
            localStorage.setItem('cart', JSON.stringify(this.cart))
        },
    }
}).mount("#app")