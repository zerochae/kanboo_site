import axios from "axios";
const moment = require("moment");

const kanban = {
  namespaced: true,
  state: {
    kanban: {
      type: "container",
      props: {
        orientation: "horizontal",
      },
      columns: [
        {
          id: 0,
          type: "container",
          name: "화면 미 구현",
          props: {
            orientation: "vertical",
          },
          cards: [],
        },
        {
          id: 1,
          type: "container",
          name: "화면 구현 완료",
          props: {
            orientation: "vertical",
          },
          cards: [],
        },
        {
          id: 2,
          type: "container",
          name: "API 개발 전",
          props: {
            orientation: "vertical",
          },
          cards: [],
        },
        {
          id: 3,
          type: "container",
          name: "API 개발 중",
          props: {
            orientation: "vertical",
          },
          cards: [],
        },
        {
          id: 4,
          type: "container",
          name: "API 개발 완료",
          props: {
            orientation: "vertical",
          },
          cards: [],
        },
      ],
    },
    showCal: false,
    showAddForm: false,
    addIndex: "",
    inputBadge: "",
    inputContent: "",
    inputDate: "",
    isUpdate: false,
    updateTarget: "",
    first: true,
    memNick: "",
    memIdx: "",
  },
  mutations: {
    setInputDate(state, payload) {
      state.inputDate = payload;
    },
    showCardMenu(state, payload) {
      let i = payload[0];
      let j = payload[1];

      for (let k = 0; k < state.kanban.columns.length; k++) {
        for (let m = 0; m < state.kanban.columns[k].cards.length; m++) {
          if(!(i == k && j == m )){
            state.kanban.columns[k].cards[m].showCardInMenu = false;
          } else {
            state.kanban.columns[i].cards[j].showCardInMenu =
                !state.kanban.columns[i].cards[j].showCardInMenu;
          }
        }
      }
    },
    showAdd(state, payload) {
      state.inputBadge = ""
      state.inputContent = ""
      state.inputDate = ""
      state.addIndex = payload;
      state.showAddForm = true;
    },
    showCalendar(state) {
      state.showCal = !state.showCal;
    },
    closeAdd(state) {
      state.showAddForm = false;
      state.showCal = false;
      state.updateTarget = "";
    },
    delete(state, payload) {
      let i = payload[0];
      let j = payload[1];
      let copyArr = [...state.kanban.columns[i].cards];
      let item = copyArr[j];
      copyArr.splice(j, 1);
      state.kanban.columns[i].cards = copyArr;
      this.dispatch("kanban/deleteKanbanItem", item);
    },
    preUpdate(state, payload) {
      let i = payload[0];
      let j = payload[1];
      state.updateTarget = state.kanban.columns[i].cards[j];
      state.isUpdate = true;
      state.showAddForm = true;
      state.inputBadge = state.updateTarget.badgeText;
      state.inputContent = state.updateTarget.content;
      state.inputDate = state.updateTarget.endDate;
    },
    update(state, payload) {

      state.showAddForm = false;
      state.isUpdate = false;

      let badge = payload[1];
      let color = payload[2];
      let endDay = payload[3];
      let content = payload[4];

      let today = moment().format("YYYY-MM-DD HH:mm:ss");
      let startDay = moment(today, "YYYY-MM-DD HH:mm:ss");

      let d_day = "D";

      let day = startDay.from(endDay).split(" ");

      if (day[day.length - 1] === "ago") {
        if (day[1] === "day" || day[1] === "days") {
          if (day[0] === "a" || day[0] === "an") {
            d_day += "-1";
          } else {
            d_day += `-${day[0]}`;
          }
        } else {
          d_day += "-1"; // 내일인데 24시간 안지났으면 이거 나와
        }
      } else {
        if (day[2] === "day" || day[2] === "days") {
          if (day[1] === "a" || day[1] === "an") {
            d_day += "+1";
          } else {
            d_day += `+${day[1]}`;
          }
        } else {
          d_day = "D-Day"; // 오늘 날짜 선택하면 이거 나와
        }
      }

      let obj = {
        content: content,
        startDate: `${today}`,
        endDate: `${endDay}`,
        badgeText: badge,
        badgeColor: color,
      };

      state.updateTarget.content = content;
      state.updateTarget.startDate = today;
      state.updateTarget.endDate = endDay;
      state.updateTarget.day = `now ${d_day}`;
      state.updateTarget.badgeText = badge;
      state.updateTarget.badgeColor = color;
      state.updateTarget.showCardInMenu = false;

      this.dispatch("kanban/updateKanbanItem", { obj });
      state.updateTarget = "";
    },
    switchCard(state) {
      let _columns = state.kanban.columns;

      for (let i = 0; i < _columns.length; i++) {
        for (let j = 0; j < _columns[i].cards.length; j++) {
          let newId = `${i}-${j}`;
          let oldId = state.kanban.columns[i].cards[j].id;
          if (newId !== oldId) {
            state.kanban.columns[i].cards[j].id = newId;
            let payload = {
              kbItmIdx: state.kanban.columns[i].cards[j].idx,
              kbItmNum: newId,
            };
            this.dispatch("kanban/saveSwitchedCard", payload);
          }
        }
      }
    },
    setDays(state, payload) {
      let day = payload[0];
      let i = payload[1];
      let j = payload[2];

      state.kanban.columns[i].cards[j].day = day;
    },
    resetUpdateTarget(state) {
      state.updateTarget = "";
    },
    add(state, payload) {
      let index = payload[0];
      let badge = payload[1];
      let color = payload[2];
      let endDay = payload[3];
      let content = payload[4];

      let id = state.kanban.columns[index].cards.length;
      let today = moment().format("YYYY-MM-DD HH:mm:ss");
      let startDay = moment(today, "YYYY-MM-DD HH:mm:ss");

      let d_day = "D";

      let day = startDay.from(endDay).split(" ");

      if (day[day.length - 1] === "ago") {
        if (day[1] === "day" || day[1] === "days") {
          if (day[0] === "a" || day[0] === "an") {
            d_day += "-1";
          } else {
            d_day += `-${day[0]}`;
          }
        } else {
          d_day += "-1"; // 내일인데 24시간 안지났으면 이거 나와
        }
      } else {
        if (day[2] === "day" || day[2] === "days") {
          if (day[1] === "a" || day[1] === "an") {
            d_day += "+1";
          } else {
            d_day += `+${day[1]}`;
          }
        } else {
          d_day = "D-Day"; // 오늘 날짜 선택하면 이거 나와
        }
      }

      state.kanban.columns[payload[0]].cards.push({
        content: content,
        id: `${payload[0]}-${id}`,
        startDate: today,
        endDate: endDay,
        day: `now (${d_day})`,
        badgeText: badge,
        badgeColor: color,
        showCardInMenu: false,
        user_name: state.memNick,
      });

      // 여기서 INSERT
      const arr = {
        content: content,
        id: `${payload[0]}-${id}`,
        startDate: today,
        endDate: endDay,
        badgeText: badge,
        badgeColor: color,
        showCardInMenu: false,
        user_name: state.memNick,
        target: state.kanban.columns[payload[0]].cards,
      };

      this.dispatch("kanban/insertKanbanItem", arr);

      state.showAddForm = false;
    },
    // updateCard(state, payload) {},
    pushTokanbanData(state, { arr, index }) {
      let cards = state.kanban.columns[index].cards;
      cards.push(arr);
      cards.sort((a, b) => {
        let idA = a.id.split("-")[1];
        let idB = b.id.split("-")[1];
        if (idA < idB) return -1;
        if (idA > idB) return 1;
      });
    },
    isFirst(state) {
      state.first = false;
    },
  },
  actions: {
    insertKanbanItem(context, arr) {
      const { content, id, startDate, endDate, badgeText, badgeColor, target } =
          arr;
      const url = "/kanban/insert";

      axios
          .post(url, null, {
            params: {
              kbItmIdx: "",
              "kanban.project.prjctIdx": sessionStorage.getItem("project"),
              "member.memIdx": context.state.memIdx,
              kbCn: content,
              kbItmNum: id,
              kbStartDate: startDate,
              kbEndDate: endDate._i,
              kbBadge: badgeText,
              kbColor: badgeColor,
            },
          })
          .then((e) => {
            target[target.length - 1].idx = e.data.kbItmIdx;
          })
          .catch();
    },
    getAllKanbanItems() {
      const url = "/kanban/getAll";
      axios
          .get(url, {
            params: {
              "kanban.project.prjctIdx": sessionStorage.getItem("project"),
            },
          })
          .then((e) => {
            for (let i = 0; i < e.data.length; i++) {
              const { data } = e;
              let tempId = data[i].kbItmNum.split("-");
              const arr = {
                content: data[i].kbCn,
                id: data[i].kbItmNum,
                startDate: String(data[i].kbStartDate).replace("T", " "),
                endDate: String(data[i].kbEndDate).replace("T", " "),
                day: "",
                badgeText: data[i].kbBadge,
                badgeColor: data[i].kbColor,
                showCardInMenu: false,
                user_name: data[i].member.memNick,
                idx: data[i].kbItmIdx,
              };
              let index = tempId[0];
              this.commit("kanban/pushTokanbanData", { arr, index });
            }
          })
          .then(this.commit("kanban/isFirst"));
    },
    deleteKanbanItem(context, item) {
      const url = "/kanban/delete";
      axios.post(url, null, {
        params: {
          kbItmIdx: item.idx,
        },
      });
    },
    updateKanbanItem(context) {
      let target = context.state.updateTarget;
      const url = "/kanban/update";
      axios.post(url, null, {
        params: {
          "member.memIdx": context.state.memIdx,
          "kanban.project.prjctIdx": sessionStorage.getItem("project"),
          kbItmIdx: target.idx,
          kbBadge: target.badgeText,
          kbColor: target.badgeColor,
          kbItmNum: target.id,
          kbStartDate: target.startDate,
          kbEndDate: target.endDate._i,
          kbCn: target.content,
        },
      });
    },
    getUserInfo(context) {
      const url = "/access/userInfo";
      axios
          .post(url, {
            token: sessionStorage.getItem("token"),
          })
          .then((result) => {
            context.state.memNick = result.data.memNick;
            context.state.memIdx = result.data.memIdx;
          });
    },
    saveSwitchedCard(context, payload) {
      const obj = {
        kbItmIdx: payload.kbItmIdx,
        kbItmNum: payload.kbItmNum,
      };

      axios({
        url: "/kanban/save",
        method: "post",
        data: obj,
      });
    },
  },
};

export default kanban;
