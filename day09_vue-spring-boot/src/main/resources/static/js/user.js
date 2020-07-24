// 创建 Vue 对象实例
var vm = new Vue({
    el: "#app", // 挂载
    data: {
        userlist: [],
        userinfo: {}
    },
    methods: {
        findAll: function () {
            axios.get('/user/findAll').then(
                function (response) {
                    console.info(response);
                    // 查询用户合集
                    vm.userlist = response.data;
                }
            );
        },
        findOne: function (id) {
            axios.get('/user/findById/' + id).then(
                function (response) {
                    console.info(response);
                    // 查询用户合集
                    vm.userinfo = response.data;
                    $('#myModal').modal('show');
                }
            );
        },
        updateUser: function () {
            // 这里的this代表vue对象
            var _this = this;
            axios.post('/user/update',this.userinfo).then(
                // 这里的this代表axios对象,注意this的指代不一样
                // 修改用户信息
                function () {
                    // 修改成功后刷新列表页面
                    _this.findAll();
                }
            );
        }
    },
    created() {
        // 页面初始化
        this.findAll();
    }
});
