import axios from 'axios'
import router from '../../../router'

const htmlCompile = {
  namespaced: true,
  state: {
    detail: "",
    chooseFile: {}
  },
  mutations: {
    setDetail(state, item) {
      state.detail = item
    },
    setChooseFile(state, item) {
      state.chooseFile = item
    }
  },
  actions: {
    getHtmlDetail(context, item) {
      if(!(router.currentRoute._rawValue.fullPath).includes("frontend")) {
        router.push("/pdtail/compiler/frontend")
      }
      axios({
        url: '/compiler/getHtml',
        method: 'post',
        data: {
          "project.prjctIdx": sessionStorage.getItem("project"),
          comIdx: item.comIdx
        }
      }).then(res => {
          context.commit("setDetail", res.data.comFileCn)
          context.commit("setChooseFile", res.data)
      })
    },
    updateHtml(context, item) {
      const obj = context.state.chooseFile
      obj.comFileCn = item
      axios({
        url: '/compiler/updateHtml',
        method: 'post',
        data: obj
      }).then(() => {})
    }
  }
}

export default htmlCompile