new Vue({
    el: "#app",
    data() {
        return {
            circleUrl: "http://localhost:8080/TopViewCinema/img/head.jpg",
            user: "",
            flag: 1,
            flag2: 1,
            changePasswordDialog: false,
            uploadMovieDialog: false,
            uploadPosterDialog: false,
            changeMovieDialog: false,
            insertTicketDialog: false,
            updateTicketDialog: false,
            bookTicketDialog: false,
            uploadHeadDialog: false,
            changeEvaluateDialog: false,
            updateSeatDialog: false,
            movie: {
                id: "",
                name: "",
                director: "",
                leadingRole: "",
                type: "",
                productionCountry: "",
                theTimeOfMovie: "",
                poster: "",
                releaseTime: "",
                price: "",
                evaluate: "",
                evaluateCount: ""
            },
            fileList: [],
            pageSizeMovies: 5,
            currentPageMovies: 1,
            totalCountMovies: 100,

            pageSizeOrders: 5,
            currentPageOrders: 1,
            totalCountOrders: 100,

            pageSizeComment: 5,
            currentPageComment: 1,
            totalCountComment: 100,
            movies: [],
            evaluateDate: {
                movieId: "",
                userId: "",
                evaluate: 0,
                comment: "",
            },
            showMovie: {
                id: "",
                name: "",
                director: "",
                leadingRole: "",
                type: "",
                productionCountry: "",
                theTimeOfMovie: "",
                poster: "",
                releaseTime: "",
                price: "",
                evaluate: "",
                evaluateCount: ""
            },
            ticket: {
                id: "",
                movieId: "",
                filmSessions: "",
                startTime: "",
                optionalSeats: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                    21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
                    41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60],
                selectedSeats: [],
                leftTicket: "",
            },
            size: '',
            tickets: [],
            order: {
                id: "",
                ticketId: "",
                userId: "",
                seat: "",
                paymentMethod: "",
                price: "",
                expirationTime: "",
                status: "",
            },
            thisOptionalSeats: [],
            orders: [],
            search: "",
            comments: [],
            seat: {
                ticketId: "",
                seat: "",
                operate: ""
            }
        }
    },
    methods: {
        changeFlag1() {
            this.flag = 1;
            this.listMovies();
        },
        changeFlag2(movie) {
            this.flag = 2;
            this.showMovie = movie;
            this.showMovie.evaluate = this.showMovie.evaluate / 10;
            this.listTicket();
        },
        changeFlag3() {
            this.flag = 3;
            this.listOrders()
        },
        changeFlag4() {
            this.flag = 4;
            this.listMoviesWithSort();
        },
        handleSizeChangeMovies(val) {
            this.pageSizeMovies = val;
            if (this.search !== "") {
                this.listMoviesByParam();
            } else {
                this.listMovies();
            }
        },
        handleCurrentChangeMovies(val) {
            this.currentPageMovies = val;
            if (this.search !== "") {
                this.listMoviesByParam();
            } else {
                this.listMovies();
            }
        },
        handleSizeChangeOrders(val) {
            this.pageSizeOrders = val;
            this.listOrders()
        },
        handleCurrentChangeOrders(val) {
            this.currentPageOrders = val;
            this.listOrders()
        },
        handleSizeChangeComment(val) {
            this.pageSizeOrders = val;
            this.listComment()
        },
        handleCurrentChangeComment(val) {
            this.currentPageOrders = val;
            this.listComment()
        },
        getUser() {
            axios({
                method: "get",
                url: "http://localhost:8080/TopViewCinema/user/getUserByToken",
                data: ''
            }).then(resp => {
                this.user = resp.data.data;
            })
        },
        changePasswordFun() {
            this.$confirm('此操作将修改你的密码, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axios({
                    method: "post",
                    url: "http://localhost:8080/TopViewCinema/user/changePassword",
                    data: this.user
                }).then(resp => {
                    alert(resp.data.msg)
                    this.changePasswordDialog = false;
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消修改'
                });
            });
        },
        handleRemove(file, fileList) {
            console.log(file, fileList);
        },
        handlePreview(file) {
            console.log(file);
        },
        uploadMovie() {
            axios({
                method: "post",
                url: "http://localhost:8080/TopViewCinema/movie/uploadMovie",
                data: this.movie
            }).then(resp => {
                alert(resp.data.msg);
                this.listMovies();
                this.uploadMovieDialog = false;
            })
        },
        listMovies() {
            axios({
                method: "get",
                url: "http://localhost:8080/TopViewCinema/movie/listMovies?pageSize=" + this.pageSizeMovies + "&currentPage=" + this.currentPageMovies + "&status=0",
                data: ''
            }).then(resp => {
                this.movies = resp.data.data.rows
                this.totalCountMovies = resp.data.data.totalCount;
            })
        },
        listMoviesByParam() {
            axios({
                method: "get",
                url: "http://localhost:8080/TopViewCinema/movie/listMoviesByParam?pageSize=" + this.pageSizeMovies + "&currentPage=" + this.currentPageMovies + "&param=" + this.search,
                data: ''
            }).then(resp => {
                this.movies = resp.data.data.rows
                this.totalCountMovies = resp.data.data.totalCount;
            })
        },
        updateMovie() {
            this.$confirm('此操作将修改电影信息, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axios({
                    method: "post",
                    url: "http://localhost:8080/TopViewCinema/movie/updateMovie",
                    data: this.showMovie
                }).then(resp => {
                    this.changeMovieDialog = false;
                    alert(resp.data.msg)
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消修改'
                });
            });
        },
        deleteMovie() {
            this.$confirm('此操作将删除该电影, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axios({
                    method: "get",
                    url: "http://localhost:8080/TopViewCinema/movie/deleteMovie?id=" + this.showMovie.id,
                    data: ''
                }).then(resp => {
                    this.changeFlag1();
                    alert(resp.data.msg);
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        insertTicketPre() {
            this.ticket.id = ""
            this.insertTicketDialog = true
        },
        insertTicket() {
            this.$confirm('是否完成添加, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axios({
                    method: "post",
                    url: "http://localhost:8080/TopViewCinema/ticket/insertTicket?id=" + this.showMovie.id,
                    data: this.ticket
                }).then(resp => {
                    alert(resp.data.msg);
                    this.listTicket();
                    this.insertTicketDialog = false
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消添加'
                });
            });
        },
        listTicket() {
            axios({
                method: "get",
                url: "http://localhost:8080/TopViewCinema/ticket/listTicket?id=" + this.showMovie.id,
                data: ''
            }).then(resp => {
                this.tickets = resp.data.data
            })
        },
        deleteTicket(id) {
            this.$confirm('此操作将删除该场次, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axios({
                    method: "get",
                    url: "http://localhost:8080/TopViewCinema/ticket/deleteTicket?id=" + id,
                    data: ''
                }).then(resp => {
                    alert(resp.data.msg);
                    this.listTicket()
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        updateTicketPre(ticket) {
            this.ticket = ticket;
            if (typeof (this.ticket.optionalSeats) === "string") {
                this.ticket.optionalSeats = this.ticket.optionalSeats.slice(1, length - 1)
                this.ticket.optionalSeats = this.ticket.optionalSeats.split(",").map(id => Number(id))
            }
            this.updateTicketDialog = true
        },
        updateTicket() {
            this.$confirm('此操作将修改该场次, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axios({
                    method: "post",
                    url: "http://localhost:8080/TopViewCinema/ticket/updateTicket",
                    data: this.ticket
                }).then(resp => {
                    alert(resp.data.msg);
                    this.listTicket()
                    this.updateTicketDialog = false
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        bookTicketPre(ticket) {
            console.log(ticket)
            if (typeof (ticket.optionalSeats) === "string") {
                ticket.optionalSeats = ticket.optionalSeats.slice(1, length - 1)
                ticket.optionalSeats = ticket.optionalSeats.split(",").map(id => Number(id))
            }
            this.thisOptionalSeats = ticket.optionalSeats;
            this.order.ticketId = ticket.id;
            this.order.userId = this.user.id
            this.order.price = this.showMovie.price
            this.order.status = 0;
            this.bookTicketDialog = true
        },
        bookTicket() {
            this.$confirm('确认订票, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axios({
                    method: "post",
                    url: "http://localhost:8080/TopViewCinema/order/insertOrder",
                    data: this.order
                }).then(resp => {
                    alert(resp.data.msg);
                    this.bookTicketDialog = false
                    this.listTicket()
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        listOrders() {
            axios({
                method: "get",
                url: "http://localhost:8080/TopViewCinema/order/listOrder?id=" + this.user.id + "&pageSize=" + this.pageSizeOrders + "&currentPage=" + this.currentPageOrders,
                data: ''
            }).then(resp => {
                this.orders = resp.data.data.rows
                this.totalCountOrders = resp.data.data.totalCount;
            })
        },
        returnTicket(row) {
            this.$confirm('确认退票, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axios({
                    method: "get",
                    url: "http://localhost:8080/TopViewCinema/order/returnTicket?id=" + row.order.id,
                    data: ''
                }).then(resp => {
                    alert(resp.data.msg);
                    this.listOrders()
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消退票'
                });
            });
        },
        deleteOrder(row) {
            this.$confirm('确认删除该订单, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axios({
                    method: "get",
                    url: "http://localhost:8080/TopViewCinema/order/deleteOrder?id=" + row.order.id,
                    data: ''
                }).then(resp => {
                    alert(resp.data.msg);
                    this.listOrders()
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消退票'
                });
            });
        },
        ticketZone() {
            this.listTicket();
            this.flag2 = 1;
        },
        commentZone() {
            this.listComment();
            this.flag2 = 2;
        },
        changeEvaluate() {
            this.evaluateDate.movieId = this.showMovie.id
            this.evaluateDate.userId = this.user.id
            this.$confirm('确认保存, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axios({
                    method: "post",
                    url: "http://localhost:8080/TopViewCinema/movie/updateEvaluate",
                    data: this.evaluateDate
                }).then(resp => {
                    alert(resp.data.msg)
                    this.changeEvaluateDialog = false
                    this.listComment();
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消保存'
                });
            });
        },
        listComment() {
            axios({
                method: "get",
                url: "http://localhost:8080/TopViewCinema/comment/listComment?pageSize=" + this.pageSizeComment + "&currentPage=" + this.currentPageComment + "&id=" + this.showMovie.id,
                data: ''
            }).then(resp => {
                this.comments = resp.data.data.rows
                this.totalCountComment = resp.data.data.totalCount;
                console.log(this.comments)
            })
        },
        updateSeatPre(ticketId) {
            this.seat.ticketId = ticketId;
            this.updateSeatDialog = true
        },
        updateSeat() {
            this.$confirm('确认提交, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axios({
                    method: "post",
                    url: "http://localhost:8080/TopViewCinema/ticket/updateSeat",
                    data: this.seat
                }).then(resp => {
                    alert(resp.data.msg)
                    this.updateSeatDialog = false
                    this.listTicket();
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消修改'
                });
            });
        },
        listMoviesWithSort() {
            axios({
                method: "get",
                url: "http://localhost:8080/TopViewCinema/movie/listMovies?pageSize=" + this.pageSizeMovies + "&currentPage=" + this.currentPageMovies + "&status=1",
                data: ''
            }).then(resp => {
                this.movies = resp.data.data.rows
                this.totalCountMovies = resp.data.data.totalCount;
            })
        },
        toService() {
            location.replace("/TopViewCinema/service.html");
        },
    },
    mounted() {
        this.getUser();
        this.listMovies();
    }
})