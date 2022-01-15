const projectList = {
  namespaced: true,
  state: {
    IsModalOpen: false,
    projectList: [],
    member: {},
    progressLength: 0,
    completeLength: 0
  },
  mutations: {
    changeIsModalOpen(state){
      state.IsModalOpen = !state.IsModalOpen
    },
    closeModal(state, e){
      for(let i in e.target.classList){
        if(e.target.classList[i] === 'bg-container' || e.target.classList[i] === 'fas'){
          state.IsModalOpen = false
        } else {
          return
        }
      }
    },
    pushToProjectList(state, item) {
      console.log(item)
      if(item.projectMemberDtoList.length < 1) {
        return
      }
      state.member = item.projectMemberDtoList[0].member
      item.projectMemberDtoList.forEach(i => {
        state.projectList.push(i.project)
      })

      state.projectList.forEach(item => {
        if(item.prjctComplAt === "n" || item.prjctComplAt === "N") {
          state.progressLength++
        } else {
          state.completeLength++
        }
      })
    },
    addCreatedProject(state, item) {
      state.projectList.push(item)
    },
    listRest(state) {
      state.projectList = []
    }
  },

  actions: {
    
  }
}

export default projectList