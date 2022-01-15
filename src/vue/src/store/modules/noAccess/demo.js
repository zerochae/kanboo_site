import axios from "axios"

const demo = {
  namespaced: true,
  state: {
    codeText: `public class Main {
      public static void main(String[] args) {
  System.out.println("hello java");
      }
  }`,
    resultCode: "",
    codeColor: "#ffffff"
  },
  mutations: {
    setCodeText(state, code) {
      state.codeText = code
    },
    setResultCode(state, code) {
      state.resultCode = code
    },
    setCodeColor(state, color) {
      state.codeColor = color
    }
  },
  actions: {
    runCompile(context) {
      const params = new URLSearchParams()
      params.append('code', context.state.codeText)

      axios.post("/noAccess/demoCompile", params)
        .then(res => {
          if(res.data.isSuccess === "false") {
            context.commit("setCodeColor", "#ff0000")
          } else {
            context.commit("setCodeColor", "#ffffff")
          }
          context.commit("setResultCode", res.data.detail)
        })
    }
  }
}

export default demo