<template>
  <div class="write-container">
    <div class="write-header">
      <span class="write-title">일감 등록</span>
      <span
        ><button @click="addTask()" class="write-btn btn">작성</button></span
      >
    </div>
    <hr class="write-line" />

    <table class="write-table">
      <tr>
        <th>상태</th>
        <td><input type="text" value="신규" readonly /></td>
        <th>담당자</th>
        <td>{{ memNick }}</td>
      </tr>
      <tr>
        <th>우선순위</th>
        <td class="select">
          <select class="selectBox" v-model="inputPriority">
            <option>낮음</option>
            <!-- #4caf50 -->
            <option>보통</option>
            <!-- #0091ff -->
            <option>높음</option>
            <!-- #ffbf00-->
            <option>긴급</option>
            <!--#ff6f00 -->
            <option>즉시</option>
            <!-- #f44336 -->
          </select>
        </td>
        <th>진척도</th>
        <td>
          <input
              type="text"
              v-model="inputProgress"
              id="progress_w"
              placeholder="0"
          />
        </td>

      </tr>
      <tr>
        <th>제목</th>
        <td>
          <input
            type="text"
            v-model="inputTitle"
            id="title_w"
            placeholder="일감 제목을 입력해주세요"
            style="width: fit-content"
          />
        </td>
        <th id="startTitle_w">시작일</th>
        <td>
          <input
              type="text"
              v-model="inputStart"
              @click="isPick(`start`)"
              id="start_w"
              placeholder="날짜를 선택하세요"
              readonly
          />
        </td>
      </tr>
      <tr>
        <th>설명</th>
        <td>
          <span>
            <input
              style="width: 230px"
              type="text"
              v-model="inputContent"
              placeholder="일감에 대한 설명을 입력해주세요."
              id="content_w"
            />
          </span>
        </td>
        <th id="endTitle_w">종료일</th>
        <td>
          <input
            type="text"
            v-model="inputEnd"
            @click="isPick(`end`)"
            id="end_w"
            placeholder="날짜를 선택하세요"
            readonly
          />
        </td>
      </tr>
    </table>
    <vue-cal
      locale="ko"
      class="vuecal--date-picker"
      xsmall
      hide-view-selector
      :time="false"
      :transitions="true"
      active-view="month"
      :disable-views="['years', 'year', 'week', 'day']"
      @cell-click="pickDate($event)"
      v-if="showCalWrite"
    >
    </vue-cal>
  </div>
</template>

<script>
import VueCal from "vue-cal";
import "vue-cal/dist/vuecal.css";
import "vue-cal/dist/i18n/ko.js";
import { mapMutations, mapState } from "vuex";
import moment from "moment";

export default {
  data() {
    return {
      inputPriority: "낮음",
      inputState: "N",
      inputProgress: "",
      startOrEndInWrite: "",
      inputContent: "",
      inputTitle: "",
      inputStart: "",
      inputEnd: "",
    };
  },
  components: {
    VueCal,
  },
  computed: {
    ...mapState({
      showCalWrite: (state) => state.gantt.showCalWrite,
      memNick: state => state.gantt.memNick
    }),
  },
  methods: {
    ...mapMutations({
      insert: "gantt/insert",
      calWriteOpen: "gantt/calWriteOpen",
      calClose: "gantt/calClose",
    }),
    pickDate(data) {
      moment.locale("ko");

      // let today = moment().format("YYYY-MM-DD").split(" ")[0];

      let selectDate = moment(data.format("YYYY-MM-DD"));
      selectDate = selectDate._i.split(" ")[0];
      this.calClose();

      switch (this.startOrEndInWrite) {
        case "start":
          this.inputStart = selectDate;
          break;
        case "end":
          this.inputEnd = selectDate;
          break;
      }
      document
        .querySelector(`#${this.startOrEndInWrite}_w`)
        .classList.remove("selectDate");
    },
    isPick(position) {
      this.calWriteOpen();

      if (this.startOrEndInWrite != "") {
        document
          .querySelector(`#${this.startOrEndInWrite}_w`)
          .classList.remove("selectDate");
      }
      document.querySelector(`#${position}_w`).classList.add("selectDate");

      switch (position) {
        case "start":
          this.startOrEndInWrite = "start";
          break;
        case "end":
          this.startOrEndInWrite = "end";
          break;
      }
    },
    addTask() {
      moment.locale("en")
      let checkList = [
        document.querySelector("#start_w"),
        document.querySelector("#end_w"),
        document.querySelector("#title_w"),
        document.querySelector("#content_w"),
      ];

      for (let item of checkList) {
        if (item.value === "") {
          item.style.boxShadow = "0px 0px 40px 40px #dd323e";
          item.style.transition = "all 0.7s ease-in-out";
          setTimeout(() => {
            item.style.boxShadow = "none";
          }, 1000);
          clearTimeout();
          return;
        }
      }

      let today = moment().format("YYYY-MM-DD").split("-");

      let payload = [];

      let start = this.inputStart.split("-");
      let end = this.inputEnd.split("-");

      let start_m = moment(start, "YYYY-MM-DD");
      let end_m = moment(end, "YYYY-MM-DD");

      if (start_m.from(end_m).split(" ")[0] == "in") {
        let target1 = document.querySelector("#start_w");
        let target2 = document.querySelector("#end_w");

        target1.style.boxShadow = "0px 0px 40px 40px #dd323e";
        target2.style.boxShadow = "0px 0px 40px 40px #dd323e";
        target1.style.transition = "all 0.7s ease-in-out";
        target2.style.transition = "all 0.7s ease-in-out";
        setTimeout(() => {
          target1.style.boxShadow = "none";
          target2.style.boxShadow = "none";
        }, 1000);
        clearTimeout();
        return;
      }

      const sDate = this.inputStart
      const eDate = this.inputEnd

      payload.push(today[1]);
      payload.push({
        memNick: this.memNick,
        title: this.inputTitle,
        content: this.inputContent,
        start: sDate,
        end: eDate,
        state: this.inputState,
        priority: this.inputPriority,
        progress: this.inputProgress == "" ? 0 : this.inputProgress,
      });

      payload.push({
        start: {
          year: start[0],
          month: start[1],
        },
        end: {
          year: end[0],
          month: end[1],
        },
      });
      this.insert(payload);

      this.inputTitle = "";
      this.inputContent = "";
      this.inputStart = "";
      this.inputEnd = "";
      this.inputState = "N";
      this.inputPriority = "낮음";
      this.inputProgress = "";
    },
  },
};
</script>

<style scoped>
.write-container {
  border-radius: 25px;
  height: calc(30vh - 40px);
  width: 49%;
  padding: 20px;
  background: #2c2f3b;
  position: relative;
  box-shadow: 3px 6px 10px rgba(255, 255, 255, 0.2) inset;
}

.vuecal--date-picker {
  position: absolute;
  top: 5%;
  right: 3%;
  box-shadow: 0px 3px 35px rgba(0, 0, 0, 0.8);
  border-radius: 15px;
  width: 210px;
  height: 230px;
  overflow: hidden;
}

.vuecal--date-picker ::-webkit-scrollbar {
  width: 0px;
}

.write-header {
  display: flex;
  justify-content: space-between;
  width: 50%;
}

.write-title {
  font-size: 18px;
}

.write-line {
  width: 50%;
  margin: 0 0 6px 0;
}

.write-btn {
  background: #ff8906;
}

.btn {
  color: white;
  margin: 0 0 10px 10px;
  border-radius: 15px;
  width: 50px;
  padding: 2px;
  box-shadow: 3px 6px 10px rgba(255, 255, 255, 0.2) inset;
  -webkit-filter: drop-shadow(0px 15px 15px rgba(10, 10, 10, 0.8));
  font-size: 12px;
}

.write-table {
  border: none;
  width: 100%;
  text-align: left;
}

.write-table th,
.write-table td {
  width: 50px;
  filter: drop-shadow(2px 4px 4px rgba(10, 10, 10, 0.8));
}

.write-table input {
  margin-top: 4px;
  background: none;
  border: none;
  outline: none;
  color: #fff;
  border-radius: 15px;
  width: fit-content;
}

.write-des-text {
}

.write-item-progress {
  right: 0;
  display: inline-block;
  height: 20px;
  max-width: 100%;
  background: #fff;
}

.write-item-progress-fill {
  height: 20px;
  width: 50%;
  background: #3f80a9;
}

.selectDate {
  color: #ff8906 !important;
}

.selectDate::placeholder {
  color: #ff8906;
}

.selectBox {
  border: none;
  background: none;
  color: #fff;
  width: fit-content;
  outline: none;
  text-align: center;
}

.selectBox :nth-child(1) {
  background: #4caf50;
}
.selectBox :nth-child(2) {
  background: #0091ff;
}
.selectBox :nth-child(3) {
  background: #ffbf00;
}
.selectBox :nth-child(4) {
  background: #ff6f00;
}
.selectBox :nth-child(5) {
  background: #f44336;
}
</style>