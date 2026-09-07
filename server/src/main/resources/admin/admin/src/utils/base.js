const base = {
    get() {
                return {
            url : "http://localhost:8080/",
            name: "gavel",
            // 退出到首页链接
            indexUrl: 'http://localhost:8080/front/index.html'
        };
            },
    getProjectName(){
        return {
            projectName: "在线拍卖系统"
        } 
    }
}
export default base
