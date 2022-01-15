import router from "@/router";

const global = {
  namespaced: true,
  state: {
    chatOn: true,
    projectIdx: 0,
  },
  mutations: {
    moveToDashBoard(state, project) {
      sessionStorage.setItem("project", project.prjctIdx)
      router.push('/pdtail/dashboard')
    },
    moveToIssue(state, issue) {
      sessionStorage.setItem("project", issue.project.prjctIdx)
      router.push('/pdtail/issue')
    },
    moveToCalendar(state, calendar) {
      sessionStorage.setItem("project", calendar.project.prjctIdx)
      router.push('/pdtail/calendar')
    },
  },
  actions: {
    
  }
}

export default global