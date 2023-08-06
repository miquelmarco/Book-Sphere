const {createApp}=Vue;

const app=createApp({
    data(){
        return{
            books:[],
            authors:[],
            events:[],
            users:[],
            userMessages:[],
            buyOrders:[],
            reservations:[],
            clients:null,
            //Book data
            isbn:"",
            bookTitle:"",
            author:"",
            category:"",
            description:"",
            image:"",
            price:"",
            stock:"",
            discount:"",
            //Featured Author data
            firstNameAuthor:"",
            lastNameAuthor:"",
            nationalityAuthor:"",
            genreAuthor:"",
            descriptionAuthor:"",
            imageAuthor:"",
            // Event data
            nameEvent:"",
            imageEvent:"",
            descriptionEvent:"",
            capacityEvent:"",
            roomEvent:"",
            timeEvent:"",
            dateEvent:"",
            //Client data
            firstNameAccount:"",
            lastNameAccount:"",
            emailAccount:"",
            passwordAccount:"",
            isAdmin:false,
            ////////////
            buttonText:'',
            displayLimit:13,
            selectedBook:null,
            showEditStatus:false,
            orderStatus:"PENDING"



        }
    },
    created() {
        axios.get("http://localhost:8080/api/books")
            .then(res => {
                const books = res.data;
                this.books=books.filter(book=>book.deleted==false)
            })
            .catch(err => console.log(err)); 

        axios.get("http://localhost:8080/api/authors")
        .then(res=>{const authors=res.data;
        this.authors=authors.filter(author=>author.deleted==false) })
        .catch(err=>console.log(err))

        axios.get("http://localhost:8080/api/events")
        .then(res=>{const events=res.data;
        this.events=events.filter(event=>event.deleted==false)})
        .catch(err=>console.log(err))

        axios.get("http://localhost:8080/api/clients")
        .then(res=>{const users=res.data;
        this.users=users.filter(user=>user.deleted==false),console.log(this.users);})
        .catch(err=>console.log(err))

        axios.get("http://localhost:8080/api/message")
        .then(res=>{const messages=res.data;
        this.userMessages=messages.filter(message=>message.deleted==false),console.log(this.userMessages);})
        .catch(err=>console.log(err))

        axios.get ('http://localhost:8080/api/clients/current')
        .then ( response => {
            this.clients=response.data;
            console.log(this.clients)


        })
        .catch(error => console.log(error));

        axios.get("http://localhost:8080/api/clients")
        .then(res => {
          const users = res.data;
          this.users = users.filter(user => user.deleted == false);
          const allBuyOrders = [];
          this.users.forEach(user => {
            const buyOrders = user.buyOrders;
            allBuyOrders.push(...buyOrders);
          });
          this.buyOrders = allBuyOrders.filter(order=>order.deleted==false);
          console.log(this.buyOrders);
        })
        .catch(err => console.log(err));

        axios.get("http://localhost:8080/api/reservation")
        .then(res=>{const reservations=res.data;this.reservations=reservations.filter(reservation=>reservation.deleted==false)})
        .catch(err=>console.log(err))
    

    },
    methods:{
        addBook() {
            const url = "http://localhost:8080/api/books/create";
            const data = {
              isbn: this.isbn,
              title: this.bookTitle,
              author: this.author,
              category: this.category,
              description: this.description,
              img: this.image,
              stock: this.stock,
              price: this.price,
              discount: this.discount,
            };
            const areAllFieldsFilled = Object.values(data).every((value) => value !== undefined && value !== "");
          
            if (!areAllFieldsFilled) {
              swal("Error", "Please enter all the data before proceeding.", "error");
              return;
            }
            swal({
              title: "Are you sure?",
              text: "Once the data is sent, it cannot be modified.",
              icon: "warning",
              buttons: true,
              dangerMode: true,
            })
            .then((willProceed) => {
              if (willProceed) {
                axios.post(url, data)
                  .then((res) => {
                    console.log(res.data);
                    swal("Success",`${res.data}`, "success"),location.reload();
                  })
                  .catch((err) => {
                    console.log(err);
                    swal("Error", `${err.response.data}`, "error");
                  });
              } else {
                swal("Cancelled", "The data was not sent.", "info");
              }
            });
          },
          
          addFeaturedAuthor() {
            const url = "http://localhost:8080/api/authors/create";
            const data = {
              firstName: this.firstNameAuthor,
              lastName: this.lastNameAuthor,
              nationality: this.nationalityAuthor,
              genre: this.genreAuthor,
              description: this.descriptionAuthor,
              img: this.imageAuthor,
            };
          
            const areAllFieldsFilled = Object.values(data).every((value) => value !== undefined && value !== "");
          
            if (!areAllFieldsFilled) {
              swal("Error", "Please enter all the data before proceeding.", "error");
              return;
            }
          
            swal({
              title: "Are you sure?",
              text: "Once the data is sent, it cannot be modified.",
              icon: "warning",
              buttons: true,
              dangerMode: true,
            })
            .then((willProceed) => {
              if (willProceed) {
                axios.post(url, data)
                  .then((res) => {
                    console.log(res.data);
                    swal("Success", `${res.data}`, "success");
                  })
                  .catch((err) => {
                    console.log(err);
                    swal("Error", `${err.response.data}`, "error");
                  });
              } else {
                swal("Cancelled", "The data was not sent.", "info");
              }
            });
          },
          addEvent() {
            const url = "http://localhost:8080/api/events/create";
            const data = {
              name: this.nameEvent,
              img: this.imageEvent,
              description: this.descriptionEvent,
              capacity: this.capacityEvent,
              time: this.timeEvent + ":00",
              date: this.changeDateFormat(this.dateEvent),
              eventRoom:this.roomEvent
            };
          
            const areAllFieldsFilled = Object.values(data).every((value) => value !== undefined && value !== "");
          
            if (!areAllFieldsFilled) {
              swal("Error", "Please enter all the data before proceeding.", "error");
              return;
            }
          
            swal({
              title: "Are you sure?",
              text: "Once the data is sent, it cannot be modified.",
              icon: "warning",
              buttons: true,
              dangerMode: true,
            })
            .then((willProceed) => {
              if (willProceed) {
                axios.post(url, data)
                  .then((res) => {
                    console.log(res.data);
                    swal("Success", `${res.data}`, "success");
                  })
                  .catch((err) => {
                    console.log(err);
                    swal("Error", `${err.response.data}`, "error");
                  });
              } else {
                swal("Cancelled", "The data was not sent.", "info");
              }
            });
          },
        addClient(){
            axios.post('http://localhost:8080/api/clients/register/admin?',`firstName=${this.firstNameAccount}&lastName=${this.lastNameAccount}&email=${this.emailAccount}&password=${this.passwordAccount}&isAdmin=${this.isAdmin}`)
            .then((res) => {
                console.log(res.data);
                swal("Success", `${res.data}`, "success"),location.reload();
              })
              .catch((err) => {
                console.log(err);
                swal("Error", `${err.response.data}`, "error");
              });
        },
         changeDateFormat(date) {
            const [year, month, day] = date.split('-');
            const newDateFormat = `${year}/${month}/${day}`;     
            return newDateFormat;
          },
          updateProperty(name) {
            if(this.buttonText!=name){ 
            this.buttonText = name;
            console.log(this.buttonText);}
            else{
                this.buttonText=""
            }
        },
        deleteBook(id, title) {
          swal({
            title: `Are you sure you want to delete the book "${title}"?`,
            icon: "warning",
            buttons: ["Cancel", "Delete"],
            dangerMode: true,
          }).then((willDelete) => {
            if (willDelete) {
              axios
                .put(`http://localhost:8080/api/books/delete?id=${id}`)
                .then((res) => {swal("Book deleted successfully.", {
                    icon: "success",
                  }),location.reload();
                })
                .catch((err) => {
                  swal("Error!", "The book could not be deleted.", "error");
                });
            }
          });
        },
        deleteAuthor(id) {
          swal({
            title: `Are you sure you want to delete this author?`,
            icon: "warning",
            buttons: ["Cancel", "Delete"],
            dangerMode: true,
          }).then((willDelete) => {
            if (willDelete) {
              axios
                .put(`http://localhost:8080/api/authors/delete?id=${id}`)
                .then((res) => {swal("Author deleted successfully.", {
                    icon: "success",
                  }),location.reload();
                })
                .catch((err) => {
                  swal("Error!", "The author could not be deleted.", "error");
                });
            }
          });
        },
        deleteEvent(id) {
          swal({
            title: `Are you sure you want to delete this event?`,
            icon: "warning",
            buttons: ["Cancel", "Delete"],
            dangerMode: true,
          }).then((willDelete) => {
            if (willDelete) {
              axios
                .put(`http://localhost:8080/api/events/delete?id=${id}`)
                .then((res) => {swal("Event deleted successfully.", {
                    icon: "success",
                  }),location.reload();
                })
                .catch((err) => {
                  swal("Error!", "The event could not be deleted.", "error");
                });
            }
          });
        },
        deleteUser(id) {
          swal({
            title: `Are you sure you want to delete this user?`,
            icon: "warning",
            buttons: ["Cancel", "Delete"],
            dangerMode: true,
          }).then((willDelete) => {
            if (willDelete) {
              axios
                .put(`http://localhost:8080/api/clients/delete?id=${id}`)
                .then((res) => {swal("User deleted successfully.", {
                    icon: "success",
                  }),location.reload()
                })
                .catch((err) => {
                  swal("Error!", "The user could not be deleted.", "error");
                });
            }
          });
        },
        deleteMessage(id) {
          swal({
            title: `Are you sure you want to delete this message?`,
            icon: "warning",
            buttons: ["Cancel", "Delete"],
            dangerMode: true,
          }).then((willDelete) => {
            if (willDelete) {
              axios
                .put(`http://localhost:8080/api/message/delete?id=${id}`)
                .then((res) => {swal("Message deleted successfully.", {
                    icon: "success",
                  }),location.reload()
                })
                .catch((err) => {
                  swal("Error!", "The message could not be deleted.", "error");
                });
            }
          });
        },

        logOut(){
          axios.post(`http://localhost:8080/api/logout`)
              .then(response => {
                  return window.location.href = "http://localhost:8080/index.html";
              })
              .catch(error => console.log(error));
      }, 

      editStatus(id) {
        swal({
          title: `Are you sure you want to edit the status?`,
          icon: "warning",
          buttons: ["Cancel", "Edit"],
          dangerMode: true,
        }).then((willEdit) => {
          if (willEdit) {
            axios.put(`http://localhost:8080/api/clients/orderStatus?id=${id}&orderStatus=${this.orderStatus}`)
              .then((res) => {
                location.reload();
              })
              .catch((err) => {

                console.log(err);
              });
          }
        });
      },
      
      showEdit(){
        this.showEditStatus=true
      },

deleteBuyOrder(id){
        swal({
          title: `Are you sure you want to delete this order?`,
          icon: "warning",
          buttons: ["Cancel", "Delete"],
          dangerMode: true,
        }).then((willDelete) => {
          if (willDelete) {
            axios
              .put(`http://localhost:8080/api/buyOrder/delete?id=${id}`)
              .then((res) => {swal("Order deleted successfully.", {
                  icon: "success",
                }),location.reload()
              })
              .catch((err) => {
                swal("Error!", "The order could not be deleted.", "error");
              });
          }
        });
      },
      deleteReservation(id){
        swal({
          title: `Are you sure you want to delete this reservation?`,
          icon: "warning",
          buttons: ["Cancel", "Delete"],
          dangerMode: true,
        }).then((willDelete) => {
          if (willDelete) {
            axios
              .put(`http://localhost:8080/api/reservation/delete?id=${id}`)
              .then((res) => {swal("Reservation deleted successfully.", {
                  icon: "success",
                }),location.reload()
              })
              .catch((err) => {
                swal("Error!", "The reservation could not be deleted.", "error");
              });
          }
        });
      },
      
        
    },

});
app.mount("#app")