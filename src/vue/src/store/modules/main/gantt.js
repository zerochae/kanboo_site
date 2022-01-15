import axios from "axios";
import moment from "moment";

const gantt = {
  namespaced: true,
  state: {
    chart: {
      id: "",
      projName: "",
      projStart: "",
      projEnd: "",
      progress: "",
      showCalWrite: false,
      showCalDetail: false,
      tasks: {},
      selectedTasks: [],
    },
    detail: "",
    openYear: "",
    openMonth: "",
    openIndex: "",
    showData: [],
    year: "",
    month: "",
    dateList: [],
    memNick: "",
    memIdx: 0,
  },
  mutations: {
    insert(state, payload) {
      let target = state.chart.tasks[payload[2].start.year][payload[0]];
      target.push(payload[1]);

      let ganttDTO = {
        "project.prjctIdx": sessionStorage.getItem("project"),
        "member.memIdx": state.memIdx,
        gtIdx: "",
        gtExplanation: payload[1].content,
        gtPriority: payload[1].priority,
        gtProgress: payload[1].progress,
        gtStartDate: payload[1].start + " 00:00:00",
        gtEndDate: payload[1].end + " 00:00:00",
        gtState: payload[1].state,
        gtTitle: payload[1].title,
      };

      axios
          .post("/gantt/insertGantt", null, {
            params: ganttDTO,
          })
          .then((result) => {
            target[target.length - 1].gtIdx = result.data.gtIdx;
          });
    },
    select(state, payload) {
      state.detail = state.chart.tasks[state.year][state.month][payload];
      state.openIndex = payload;
      state.openMonth = state.month;
      state.openYear = state.year;
    },
    update(state, payload) {
      state.chart.tasks[state.openYear][state.openMonth][state.openIndex] =
          payload;

      let ganttDTO = {
        gtIdx: payload.gtIdx,
        gtExplanation: payload.content,
        gtPriority: payload.priority,
        gtProgress: payload.progress,
        gtStartDate: payload.start,
        gtEndDate: payload.end,
        gtState: payload.state,
        gtTitle: payload.title,
      };

      state.detail =
          state.chart.tasks[state.year][state.month][state.openIndex];
      console.log(ganttDTO)

      axios.post("/gantt/updateGantt", null, {
        params: ganttDTO,
      });
    },
    delete(state, payload) {
      state.chart.tasks[state.openYear][state.openMonth].splice(
          state.openIndex,
          1
      );

      state.detail = "";

      let ganttDTO = {
        gtIdx: payload,
      };

      axios.post("/gantt/deleteGantt", null, {
        params: ganttDTO,
      });
    },
    calWriteOpen(state) {
      state.showCalWrite = true;
      state.showCalDetail = false;
    },
    calDetailOpen(state) {
      state.showCalWrite = false;
      state.showCalDetail = true;
    },
    calClose(state) {
      state.showCalWrite = false;
      state.showCalDetail = false;
    },
    setGanttData(state, payload) {
      state.chart.tasks = payload;
      state.year = moment().format("YYYY")
      state.month = moment().format("MM")
    },
    pushGanttData(state, payload) {
      let year = payload[0];
      let month = payload[1];
      state.chart.tasks[year][month].push(payload[2]);
    },
    selectedTasks(state) {
      state.selectedTasks = state.chart.tasks[2021][12];
    },
    renderDate(state) {
      state.dateList = [];
      let today = moment().format("YYYY-MM-DD").split("-");
      if (state.year === "") {
        state.year = today[0];
      }

      if (state.month === "") {
        state.month = today[1];
      }

      let lastDay = new Date(state.year, state.month, 0).getDate();

      for (let day = 1; day < lastDay + 1; day++) {
        if (day < 10) {
          day = `0${day}`;
        }
        state.dateList.push(`${day}`);
      }
    },
    setShowList(state, item) {
      try {
        state.showData = item[state.year][state.month];
      } catch (err) {
        state.showData = item
      }
    },
    renderChart(state) {
      let days = document.querySelectorAll(".chart-date li");
      let tasks = document.querySelectorAll(".chart-bars li");
      days = Array.from(days);
      tasks = Array.from(tasks);

      let left = 0,
          width = 0,
          f_arr = [];

      tasks.forEach((el, index) => {
        if(state.showData[index].start.split(" ")[0].split("-")[1] == state.month) {
          let start = state.showData[index].start.split(" ")[0].split("-")[2];

          f_arr = days.filter((day) => day.textContent === start);

          left = f_arr[0].offsetLeft - 20;

          let end = state.showData[index].end.split(" ")[0].split("-")[2];

          f_arr = days.filter((day) => day.textContent === end);
          width = f_arr[0].offsetLeft + f_arr[0].offsetWidth - left - 20;

          el.style.left = `${left}px`;
          el.style.width = `${width}px`;

          el.style.opacity = 1;
        }
      });
    },
    setPrevMonth(state) {
      if(state.month === 1) {
        state.month = 12
        state.year--
      } else {
        state.month--
      }
    },
    setNextMonth(state) {
      if(state.month === 12) {
        state.month = 1
        state.year++
      } else {
        state.month++
      }
    }
  },
  actions: {
    getGanttData(context) {
      axios
          .get("/gantt/selectGantt", {
            params: {
              projectIdx: sessionStorage.getItem("project"),
            },
          })
          .then((result) => {
            let temp = {};
            if(result.data.length > 0) {
              result.data.map((item) => {
                const date = item.gtStartDate.split("T")[0].split("-");
                const year = date[0];
                const month = date[1];

                if (temp[year] == null) temp[year] = {};
                if (temp[year][month] == null) temp[year][month] = [];

                const obj = {
                  gtIdx: item.gtIdx,
                  memNick: item.member.memNick,
                  title: item.gtTitle,
                  content: item.gtExplanation,
                  start: item.gtStartDate.replace("T", " "),
                  end: item.gtEndDate.replace("T", " "),
                  state: item.gtState,
                  priority: item.gtPriority,
                  progress: item.gtProgress,
                };
                temp[year][month].push(obj);
              });
            } else {
              const date = moment().format("YYYY-MM-DD").split("-")
              const year = date[0];
              const month = date[1];

              temp[year] = {};
              temp[year][month] = [];
            }
            context.commit("setGanttData", temp);
            context.commit("setShowList", temp);
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
  },
};

export default gantt;
