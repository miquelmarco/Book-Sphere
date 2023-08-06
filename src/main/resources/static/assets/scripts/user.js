const { createApp } = Vue;

const app = createApp ({

    data (){
        return {

            clients:"",
            buyOrders: {},
            shippingAddress: {},
            address: "",
            //otros
            searchInput2: '',
            books: [],
            filter: [],
            selectedBook: null,
            categories: [],
            authors: [],
            discounts: [],
            cart: [],
            reservations:[],
            //ADDRESS
            editMode: false,
            shippingAddress: "",
            address: "",

        }
    },


    created (){
        this.loadData();
        this.loadCartFromLocalStorage()

    },

    methods: {
        loadData(){
            axios.get('http://localhost:8080/api/clients/current')
            .then(response => {
                this.clients = response.data;
                console.log(this.clients);
        
                this.buyOrders = response.data.buyOrders;
                console.log(this.buyOrders);
        
                this.shippingAddress = response.data.shippingAdress;
                console.log(this.shippingAddress);
        
                this.reservations = response.data.reservations.sort((a, b) => a.id - b.id);
                console.log(this.reservations);
        
            })
            .catch(error => console.log(error));
        },

        editShipping() {
            this.editMode = true;
            this.address = this.shippingAddress;
        },

        setShippingAddress() {
            this.address = this.address.trim();
            if (this.address === "" || this.address.toLowerCase() === "null") {
                Swal.fire({
                    position: 'center',
                    icon: 'error',
                    title: 'Please enter a valid shipping address!',
                    showConfirmButton: false,
                    timer: 1500
                });
                return;
            }
            axios.put(`http://localhost:8080/api/clients/address?shippingAdress=${this.address}`)
                .then(response => {
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Your shipping address has been successfully created!',
                        showConfirmButton: false,
                        timer: 1000
                    });
                    setTimeout(() => {
                        window.location.reload();
                    }, 1800);
                })
                .catch(error => {
                    Swal.fire({
                        position: 'center',
                        icon: 'error',
                        title: 'Incorrect, try again!',
                        showConfirmButton: false,
                        timer: 1500
                    });
                });
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

        logOut(){
            axios.post(`http://localhost:8080/api/logout`)
                .then(response => {
                    return window.location.href = "http://localhost:8080/index.html";
                })
                .catch(error => console.log(error));
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
    }

})
app.mount("#app");