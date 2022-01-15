import axios from "axios";

const userProject = {
  namespaced: true,
  state: {
    projectData: [],
    isAxiosRunning: false,
    articleOnView: 0,
    maxIndex: "",
    key: "",
    selected: "All",
  },
  mutations: {
    update(state, payload) {
      let target = state.projectData.find((data) => data.idx == payload[0]);
      let url = "/admin/updateProjectState";
      let projectDTO = {
        prjctIdx: target.idx,
      };
      if (payload[1] == "삭제") {
        target.isDel = "y";
      } else {
        target.isDel = "n";
        target.isCom = payload[1] === "진행" ? "n" : "y";
      }
      projectDTO.prjctDelAt = target.isDel;
      projectDTO.prjctComplAt = target.isCom;

      axios
          .post(url, null, {
            params: projectDTO,
          })
          .then((result) => {
            console.log(result);
          });
    },
    setProjects(state, payload) {
      state.projectData.push(payload);
    },
    setArticleOnView(state, payload) {
      state.articleOnView += payload;
    },
    setMaxIndex(state, payload) {
      state.maxIndex = payload;
    },
    setAxiosState(state, payload) {
      state.isAxiosRunning = payload;
    },
    setSelected(state, payload) {
      state.selected = payload;
    },
    setKeyWord(state, payload) {
      state.key = payload;
    },
    resetData(state){
      state.projectData = [];
      state.articleOnView = 0;
    }
  },
  actions: {
    setProjectList(context) {
      axios
          .get("/admin/getAllProject", {
            params: {
              selected: context.state.selected,
              key: context.state.key,
              articleOnView: context.state.articleOnView,
            },
          })
          .then((result) => {
            for (let item of result.data) {
              context.commit("setProjects", {
                idx: item.project.prjctIdx,
                name: item.project.prjctNm,
                memId: item.member.memId,
                progress: item.project.prjctProgress,
                isDel: item.project.prjctDelAt,
                isCom: item.project.prjctComplAt,
                start: item.project.prjctStartDate,
                end: item.project.prjctEndDate,
              });
            }
            context.commit("setArticleOnView", result.data.length);
          });
    },
    getMoreList(context) {
      axios
          .get("/admin/getAllProject", {
            params: {
              selected: context.state.selected,
              key: context.state.key,
              articleOnView: context.state.articleOnView,
            },
          })
          .then((result) => {
            for (let item of result.data) {
              context.commit("setProjects", {
                idx: item.project.prjctIdx,
                name: item.project.prjctNm,
                memId: item.member.memId,
                progress: item.project.prjctProgress,
                isDel: item.project.prjctDelAt,
                isCom: item.project.prjctComplAt,
                start: item.project.prjctStartDate,
                end: item.project.prjctEndDate,
              });
            }
            context.commit("setArticleOnView", result.data.length);
            context.commit("setAxiosState", false);
          });
    },
    getMaxProjectIndex(context) {
      axios
          .get("/admin/getMaxIndexOfProject", {
            params: {
              selected: context.state.selected,
              key: context.state.key,
            },
          })
          .then((result) => {
            context.commit("setMaxIndex", result.data);
          });
    },
  },
};

export default userProject;
