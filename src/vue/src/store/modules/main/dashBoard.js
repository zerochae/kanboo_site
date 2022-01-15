import axios from "axios"

const dashBoard = {
    namespaced: true,
    state: {
        gantt: [],
        progress: 0,
        memberList: [],
        gitCommit: [],
        issue: [],
        finishDay: 0,
        boardList: [],
        scheduleList: [],
        readMeContent: "",
        projectIdx: 1,
        start: "",
        end: ""
    },
    mutations: {
        setReadMeContent(state, contents) {
            state.readMeContent = contents
        },
        setMemberList(state, item) {
            state.memberList = item
        }
    },
    actions: {
        saveReadMe(context, contents) {
            const url = "/pdtail/saveReadMe"
            axios.post(url, null, {
                params: {
                    prjctIdx: 1,
                    prjctReadMe: contents
                }
            })
                .then(res => {
                    console.log(res)
                    if (res) {
                        context.commit("setReadMeContent", contents)
                    }
                })
        },
        mountGetData(context) {
            const url = "/dashboard/getData"
            axios.get(url, {
                params: {
                    prjctIdx: sessionStorage.getItem("project"),
                }
            })
                .then(res => {
                    console.log(res.data)
                    context.commit("setMemberList", res.data.projectMemberList)
                    // context.commit("setReadMeContent", res.data.project.prjctReadMe)
                })
        }
    }
}

export default dashBoard