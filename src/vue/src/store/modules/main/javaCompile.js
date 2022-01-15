import axios from "axios"
import arrayToTree from "array-to-tree";
import router from "@/router";

const javaCompile = {
  namespaced: true,
  state: {
    isOpen: false,
    name: "",
    type: ['Directory', 'File'],
    path: [],
    classification: ['JAVA'],
    requestData: {},
    dataObj: [],
    code: ``,
    clickedFile: {},
    result: "",
    isError: "",
    axiosPath: "",
    htmlList: [],
    isJava: false
  },
  mutations: {
    openAddModal(state, item) {
      if(item === 'java') {
        state.requestData = {
          name: "",
          type: 'Directory',
          path: null,
          classification: 'JAVA'
        }
        state.isJava = true
      } else {
        state.requestData = {
          name: "",
          type: 'File',
          path: null,
          classification: 'HTML'
        }
        state.isJava = false
      }
      state.isOpen = !state.isOpen
    },
    addPath(state, arr) {
      const result = []
      arr.forEach(item => {
        if(item.classification === 'directory') {
          result.push(item)
        }
      })
      state.path = result
    },
    getJavaSideBar(state) {
      axios({
        url: '/compiler/getData',
        method: 'post',
        data: {
          prjctIdx: sessionStorage.getItem("project")
        }
      }).then(res => {
        state.dataObj.splice(0, state.dataObj.length)
        const arr = []
        res.data.javaList.forEach(node => {
          const obj = {
            id: node.comIdx,
            classification: node.comSe === "d" ? "directory" : (node.comSe === "f" ? "file" : "html"),
            label: node.comNm,
            parent_id: node.parentComIdx
          }
          arr.push(obj)
        })

        arrayToTree(arr, {childrenProperty: 'nodes'}).forEach(item => {
          if(item.classification !== "html") {
            state.dataObj.push(item)
          }
        })

        this.commit("javaCompile/addPath", arr)

        res.data.htmlList.forEach(item => {
          state.htmlList.push(item)
        })
      })
    },
    setCode(state, item) {
      state.code = item
    },
    setClickedFile(state, item) {
      state.clickedFile = item
    },
    setIsError(state, item) {
      if(item === 'true') {
        state.isError = "#fff"
      } else {
        state.isError = "#ff0000"
      }
    },
    setResult(state, item) {
      state.result = item
    },
    recursiveFindPath(state, item) {
      if(item.parent_id !== null) {
        state.axiosPath = item.label + "/" + state.axiosPath
        const ele = state.path.find(p => p.id === item.parent_id)
        this.commit("javaCompile/recursiveFindPath", ele)
      }
    },
    axiosPathReset(state) {
      state.axiosPath = ""
    },
    addCreatedFile(state, item) {
      state.htmlList.push(item)
    }
  },
  actions: {
    createDirOrFile(context) {
      if(context.state.isJava) {
        const obj = context.state.requestData
        const params = new URLSearchParams();
        context.commit("recursiveFindPath", obj.path)
        const p = context.state.axiosPath

        params.append("name", obj.name)
        params.append("path", p)
        params.append("type", obj.type)
        params.append("classification", obj.classification)
        params.append("projectIdx", sessionStorage.getItem("project"))
        params.append("comIdx", obj.path.id)

        const url = "/pdtail/addDirOrFile"
        axios.post(url, params)
            .then(() => {
              context.commit("getJavaSideBar")
              context.commit("axiosPathReset")
            })
      } else {
        const obj = context.state.requestData
        axios({
          url: '/compiler/createHtml',
          method: 'post',
          data: {
            prjctIdx: sessionStorage.getItem("project"),
            name: obj.name
          }
        }).then(res => {
          context.commit("addCreatedFile", res.data.compiler)
        })
      }
      context.commit("openAddModal")
    },
    getFileDetail(context, item) {
      if(!(router.currentRoute._rawValue.fullPath).includes("backend")) {
        router.push("/pdtail/compiler/backend")
      }

      if(item.classification === "file") {
        const url = '/compiler/getFile'

        const params = {
          comIdx: item.id,
          project: {
            prjctIdx: sessionStorage.getItem("project")
          }
        }

        axios.post(url, params).then(res => {
          context.commit("setClickedFile", res)
          context.commit('setCode', res.data.comFileCn)
        })
      }
    },
    updateCode(context, item) {
      const url = "/compiler/updateFile"
      const obj = {
        comFileCn: item,
        comFileIdx: context.state.clickedFile.data.comFileIdx,
        compiler: {
          comIdx: context.state.clickedFile.data.compiler.comIdx,
          comNm: context.state.clickedFile.data.compiler.comNm,
          comSe: context.state.clickedFile.data.compiler.comSe,
          parentComIdx: context.state.clickedFile.data.compiler.parentComIdx,
          project: {
            prjctIdx: context.state.clickedFile.data.compiler.project.prjctIdx
          }
        }
      }
      axios.post(url, obj).then(res => {
        res
      })
    },
    runCompile(context) {
      const url = "/compiler/runCompile"
      const obj = {
        prjctIdx: sessionStorage.getItem("project")
      }
      axios.post(url, obj).then(res => {
        context.commit("setIsError", res.data.isSuccess)
        context.commit("setResult", res.data.detail)
      })
    }
  },
}

export default javaCompile