import axios from "axios";
import moment from "moment";

const adminQnA = {
  namespaced: true,
  state: {
    qnaData: [],
    isAxiosRunning: false,
    articleOnView: 0,
    maxIndex: "",
    key: "",
    selected: "All",
  },
  mutations: {
    insert(state, payload) {
      let index = payload[0];
      let data = payload[1];
      let today = moment().format("YYYY-MM-DD HH:mm:ss");
      let target = state.qnaData[index].replies;

      target.push(data);

      let CommentDTO = {
        "member.memTag": "#eEG8l",
        // "member.memTag": sessionStorage.getItem('memTag'),
        "board.boardIdx": data.boardIdx,
        answerCn: data.content,
        answerDate: today,
      };

      axios
          .post("/admin/insertComment", null, {
            params: CommentDTO,
          })
          .then((result) => {
            target[target.length - 1] = {
              commentIdx: result.data.answerIdx,
              memId: result.data.member.memId,
              content: result.data.answerCn,
              date: result.data.answerDate,
            };
          });
    },
    setQna(state, payload) {
      state.qnaData.push(payload);
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
      state.qnaData = [];
      state.articleOnView = 0;
    },
  },
  actions: {
    setQnaList(context) {
      axios
          .get("/admin/getQnaBoardList", {
            params: {
              selected: "",
              key: "",
              articleOnView: context.state.articleOnView,
              codeDetail: "8",
            },
          })
          .then((result) => {
            for (let item of result.data) {
              let data = [];
              for (let reply of item.commentList) {
                data.push({
                  replyIdx: reply.answerIdx,
                  memNick : "admin",
                  date : reply.answerDate.replace("T"," "),
                  content: reply.answerCn
                });
              }

              context.commit("setQna", {
                boardIdx: item.boardIdx,
                memNick: item.member.memNick,
                date: item.boardDate,
                content: item.boardCn,
                replies: data,
              });
            }
            context.commit("setArticleOnView", result.data.length);
          });
    },
    getMoreList(context) {
      axios
          .get("/admin/getQnaBoardList", {
            params: {
              selected: context.state.selected,
              key: context.state.key,
              articleOnView: context.state.articleOnView,
              codeDetail: "8",
            },
          })
          .then((result) => {
            for (let item of result.data) {
              let data = [];
              for (let reply of item.commentList) {
                data.push({
                  replyIdx: reply.answerIdx,
                  memNick : "admin",
                  date : reply.answerDate.replace("T"," "),
                  content: reply.answerCn
                });
              }

              context.commit("setQna", {
                boardIdx: item.boardIdx,
                memNick: item.member.memNick,
                date: item.boardDate,
                content: item.boardCn,
                replies: data,
              });
            }
            context.commit("setArticleOnView", result.data.length);
            context.commit("setAxiosState");
          });
    },
    getMaxBoardIndex(context) {
      axios
          .get("/board/getArticleNum", {
            params: {
              key: context.state.key,
              selected: context.state.selected,
              codeDetails: "8",
            },
          })
          .then((result) => {
            context.commit("setMaxIndex", result.data);
          });
    },
  },
};

export default adminQnA;
