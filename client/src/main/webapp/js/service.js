new Vue({
    el: "#app",
    data() {
        return {
            flag: 1,
            ServiceProviders: [],
            Hosts: [],
            ServiceCallDates: [],
            SystemCondition: "",
        }
    },
    methods: {
        changeFlag1() {
            this.flag = 1;
            this.listServices();
        },
        changeFlag2() {
            this.flag = 2;
            this.listHosts();
        },
        changeFlag3() {
            this.flag = 3;
            this.listServiceCallDates();
        },
        changeFlag4() {
            this.flag = 4;
            this.getSystemCondition();
        },
        init() {
            let that = this;
            setInterval(function () {
                if (that.flag === 1) {
                    that.listServices();
                } else if (that.flag === 2) {
                    that.listHosts();
                } else if (that.flag === 3) {
                    that.listServiceCallDates();
                } else if (that.flag === 4) {
                    that.getSystemCondition();
                }
            },3000)
        },
        listServices() {
            axios({
                method: "get",
                url: "http://localhost:8080/TopViewCinema/service/listService",
                data: ''
            }).then(resp => {
                this.ServiceProviders = resp.data.data;
            })
        },
        listHosts() {
            axios({
                method: "get",
                url: "http://localhost:8080/TopViewCinema/service/listHosts",
                data: ''
            }).then(resp => {
                this.Hosts = resp.data.data;
            })
        },
        listServiceCallDates() {
            axios({
                method: "get",
                url: "http://localhost:8080/TopViewCinema/service/listServiceCallDates",
                data: ''
            }).then(resp => {
                this.ServiceCallDates = resp.data.data;
            })
        },
        getSystemCondition() {
            axios({
                method: "get",
                url: "http://localhost:8080/TopViewCinema/service/getSystemCondition",
                data: ''
            }).then(resp => {
                this.SystemCondition = resp.data.data;
            });
        }
    },
    mounted() {
        this.init();
        this.listServices();
    }
})