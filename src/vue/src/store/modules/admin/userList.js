import moment from "moment";
import axios from "axios";

const userList = {
  namespaced: true,
  state: {
    userListData: [],
    isAxiosRunning: false,
    articleOnView: 0,
    maxIndex: "",
    key: "",
    selected: "All",
  },
  mutations: {
    update(state, payload) {
      let today = moment().format("YYYY-MM-DD");
      let BanDTO = {};
      let target = state.userListData.find((data) => data.memIdx == payload[0]);
      if (payload[1] == "정상") {
        target.start = "";
        target.end = "";

        BanDTO = {
          banIdx: target.banIdx,
        };

        axios.post("/admin/deleteBanUser", null, {
          params: BanDTO,
        });
      } else {
        target.start = today;

        switch (payload[1]) {
          case "7일 정지":
            target.end = moment().add(1, "week").format("YYYY-MM-DD");
            break;
          case "30일 정지":
            target.end = moment().add(30, "days").format("YYYY-MM-DD");
            break;
          case "영구 정지":
            target.end = "9999-12-31";
            break;
        }
        BanDTO = {
          banIdx: target.banIdx,
          banStartDate: today,
          banEndDate: target.end,
          "member.memIdx": payload[0],
        };

        axios
            .post("/admin/updateBanUser", null, {
              params: BanDTO,
            })
            .then((result) => {
              target.banIdx = result.data.banIdx;
            });
      }
    },
    setMembers(state, payload) {
      state.userListData.push(payload);
    },
    setArticleOnView(state, payload) {
      state.articleOnView += payload;
    },
    setMaxIndex(state, payload) {
      state.maxIndex = payload;
    },
    setAxiosState(state) {
      state.isAxiosRunning = !state.isAxiosRunning;
    },
    setSelected(state,payload){
      state.selected = payload
    },
    setKeyWord(state,payload){
      state.key = payload;
    },
    resetData(state){
      state.userListData = [];
      state.articleOnView = 0;
    }
  },
  actions: {
    setMemberList(context) {
      axios
          .get("/admin/getAllMember", {
            params: {
              selected: context.state.selected,
              key: context.state.key,
              articleOnView: context.state.articleOnView,
            },
          })
          .then((result) => {
            for (let item of result.data) {
              context.commit("setMembers", {
                tag: item.memTag,
                id: item.memId,
                memIdx: item.memIdx,
                banIdx: item.ban != null ? item.ban.banIdx : "",
                start:
                    item.ban != null ? item.ban.banStartDate.split("T")[0] : "",
                end: item.ban != null ? item.ban.banEndDate.split("T")[0] : "",
              });
            }
            context.commit("setArticleOnView", result.data.length);
          });
    },
    getMoreList(context) {
      axios
          .get("/admin/getAllMember", {
            params: {
              selected: context.state.selected,
              key: context.state.key,
              articleOnView: context.state.articleOnView,
            },
          })
          .then((result) => {
            for (let item of result.data) {
              context.commit("setMembers", {
                tag: item.memTag,
                id: item.memId,
                memIdx: item.memIdx,
                banIdx: item.ban != null ? item.ban.banIdx : "",
                start:
                    item.ban != null ? item.ban.banStartDate.split("T")[0] : "",
                end: item.ban != null ? item.ban.banEndDate.split("T")[0] : "",
              });
            }
            context.commit("setArticleOnView", result.data.length);
            context.commit("setAxiosState");
          });
    },
    getMaxMemberIndex(context) {
      axios
          .get("/admin/getMaxIndexOfMember", {
            params: {
              key: context.state.key,
              selected: context.state.selected,
            },
          })
          .then((result) => {
            context.commit("setMaxIndex", result.data);
          });
    },
  },
};

export default userList;
