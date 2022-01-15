<template>
  <div class="detail-container">
    <div class="detail-header">
      <span v-if="!isUpdate" class="detail-title">일감 상세</span>
      <span v-else-if="isUpdate" class="detail-title">일감 수정</span>
      <span class="button-zone" v-if="detail != '' && !isUpdate">
        <button @click="isUpdate = true" class="update-btn btn">수정</button>
        <button @click="deleteLine" class="delete-btn btn">삭제</button>
      </span>
      <span class="button-zone" v-if="isUpdate">
        <button @click="completeModify" class="update-btn btn">완료</button>
        <button @click="cancelModify" class="delete-btn btn">취소</button>
      </span>
    </div>
    <hr class="detail-line" />

    <!-- 선택 안했을때 (detail == null) 보여질 Element 시작 -->
    <table v-if="detail == ''" class="detail-table">
      <tr>
        <td>상단에서 일감을 선택해주세요</td>
      </tr>
    </table>
    <!-- 선택 안했을때 (detail == null) 보여질 Element 끝 -->

    <!-- 선택 했을때 (detail != null) 보여질 Element 시작 -->
    <table v-else-if="detail != '' && !isUpdate" class="detail-table">
      <tr>
        <th>상태</th>
        <td>
          <input
            type="text"
            :value="detail.state == 'N' ? '진행' : '완료'"
            readonly
          />
        </td>
        <th>담당자</th>
        <td>{{ detail.memNick }}</td>
        </tr>
      <tr>
      <th>우선순위</th>
      <td><input type="text" :value="detail.priority" readonly /></td>
        <th>진척도</th>
        <td class="detail-progress">
          <div class="detail-item-progress">
            <div
              :style="{
                background: setColor(detail.priority),
                width: `${detail.progress}%`,
              }"
              class="detail-item-progress-fill"
            ></div>
          </div>
          <span class="detail-item-progress-percent"
            >{{ detail.progress }} %</span
          >
        </td>
      </tr>
      <tr>
        <th>제목</th>
        <td>
          <input type="text" :value="detail.title" readonly />
        </td>
        <th>시작일</th>
        <td>
          <input
            type="text"
            :value="detail.start.split(' ')[0]"
            readonly
          />
        </td>
      </tr>
      <tr>
        <th>설명</th>
        <td>
          <input type="text" :value="detail.content" readonly />
        </td>
        <th>종료일</th>
        <td>
          <input
            type="text"
            :value="detail.end.split(' ')[0]"
            readonly
          />
        </td>
      </tr>
    </table>
    <!-- 선택 했을때 (detail != null) 보여질 Element 끝 -->

    <!-- 수정 모드 일때 (isUpdate == true) 보여질 Element 시작 -->
    <table v-else-if="isUpdate" class="detail-table">
      <tr>
        <th>상태</th>
        <td>
          <select class="selectBox state" @change="selectState">
            <option :selected="detail.state == 'N'" value="N">진행</option>
            <option :selected="detail.state == 'Y'" value="Y">완료</option>
          </select>
        </td>
        <th>담당자</th>
        <td>{{ detail.memNick }}</td>
        </tr>
      <tr>
        <th>우선순위</th>
        <td>
          <select class="selectBox priority" @change="selectPriority">
            <option :selected="detail.priority == '낮음'" value="낮음">
              낮음
            </option>
            <!-- #4caf50 -->
            <option :selected="detail.priority == '보통'" value="보통">
              보통
            </option>
            <!-- #0091ff -->
            <option :selected="detail.priority == '높음'" value="높음">
              높음
            </option>
            <!-- #ffbf00-->
            <option :selected="detail.priority == '긴급'" value="긴급">
              긴급
            </option>
            <!--#ff6f00 -->
            <option :selected="detail.priority == '즉시'" value="즉시">
              즉시
            </option>
            <!-- #f44336 -->
          </select>
        </td>
        <th>진척도</th>

        <td class="detail-progress">
          <div class="detail-item-progress">
            <div
              :style="{
                background: setColor(
                  inputPriority == '' ? detail.priority : inputPriority
                ),
                width: `${
                  inputProgress == '' ? detail.progress : inputProgress
                }%`,
              }"
              class="detail-item-progress-fill"
            ></div>
          </div>
          <span class="detail-item-progress-percent update">
            <input
              type="text"
              @input="setProgress"
              :placeholder="detail.progress"
            />
            %
          </span>
        </td>
      </tr>
      <tr>
        <th>제목</th>
        <td>
          <input type="text" v-model="inputTitle" :placeholder="detail.title" />
        </td>
        <th>시작일</th>
        <td>
          <input
            type="text"
            :placeholder="detail.start.split(' ')[0]"
            v-model="inputStart"
            @click="isPick(`start`)"
            id="start_d"
            readonly
          />
        </td>
      </tr>
      <tr>
        <th>설명</th>
        <td>
          <input
              type="text"
              v-model="inputContent"
              :placeholder="detail.content"
          />
        </td>
        <th>종료일</th>
        <td>
          <input
            type="text"
            :placeholder="detail.end.split(' ')[0]"
            v-model="inputEnd"
            @click="isPick(`end`)"
            id="end_d"
            readonly
          />
        </td>
      </tr>
    </table>
    <!-- 수정 모드 일때 (isUpdate == true) 보여질 Element 끝 -->
    <vue-cal
      locale="ko"
      class="vuecal--date-picker cal"
      xsmall
      hide-view-selector
      :time="false"
      :transitions="true"
      active-view="month"
      :disable-views="['years', 'year', 'week', 'day']"
      @cell-click="pickDate($event)"
      v-if="showCalDetail"
    >
    </vue-cal>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import VueCal from "vue-cal";
import "vue-cal/dist/vuecal.css";
import "vue-cal/dist/i18n/ko.js";
import moment from "moment";

export default {
  data() {
    return {
      isUpdate: false,
      inputPriority: "",
      inputState: "",
      inputProgress: "",
      startOrEndInDetail: "",
      inputContent: "",
      inputTitle: "",
      inputStart: "",
      inputEnd: "",
    };
  },
  components: {
    VueCal,
  },
  watch: {
    detail: function (oldVal, newVal) {
      if (oldVal !== newVal) {
        this.isUpdate = false;
        this.calClose();
      }
    },
  },
  computed: {
    ...mapState({
      detail: (state) => state.gantt.detail,
      openYear: (state) => state.gantt.openYear,
      openMonth: (state) => state.gantt.openMonth,
      showCalDetail: (state) => state.gantt.showCalDetail,
    }),
  },
  methods: {
    ...mapMutations({
      update: "gantt/update",
      delete: "gantt/delete",
      calDetailOpen: "gantt/calDetailOpen",
      calClose: "gantt/calClose",
    }),
    deleteLine() {
      let payload = this.detail.gtIdx;
      this.delete(payload);
    },
    completeModify() {
      let start = this.inputStart == "" ? this.detail.start : this.inputStart + " 00:00:00";

      let end = this.inputEnd == "" ? this.detail.end : this.inputEnd + " 00:00:00";

      let start_m = moment(start, "YYYY-MM-DD");
      let end_m = moment(end, "YYYY-MM-DD");

      if (start_m.from(end_m).split(" ")[0] == "in") {
        let target1 = document.querySelector("#start_d");
        let target2 = document.querySelector("#end_d");

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

      this.isUpdate = false;

      let payload = {
        gtIdx: this.detail.gtIdx,
        title: this.inputTitle == "" ? this.detail.title : this.inputTitle,
        content:
          this.inputContent == "" ? this.detail.content : this.inputContent,
        start: this.inputStart == "" ? this.detail.start : start,
        end: this.inputEnd == "" ? this.detail.end : end,
        state: this.inputState == "" ? this.detail.state : this.inputState,
        priority:
          this.inputPriority == "" ? this.detail.priority : this.inputPriority,
        progress:
          this.inputProgress == "" ? this.detail.progress : this.inputProgress,
      };

      this.update(payload);
      this.resetSelectedData();
      this.calClose();
    },
    cancelModify() {
      this.isUpdate = false;
      this.resetSelectedData();
      this.calClose();
    },
    setProgress(e) {
      let data = e.target.value;
      if (data > 100) {
        return;
      } else {
        this.inputProgress = data;
      }
    },
    pickDate(data) {
      moment.locale("ko");
      let selectDate = moment(data.format("YYYY-MM-DD"));
      selectDate = selectDate._i;

      this.calClose();

      switch (this.startOrEndInDetail) {
        case "start":
          this.inputStart = selectDate;
          break;
        case "end":
          this.inputEnd = selectDate;
          break;
      }
      document
        .querySelector(`#${this.startOrEndInDetail}_d`)
        .classList.remove("selectDate");

    },
    isPick(position) {
      this.calDetailOpen();
      if (this.startOrEndInDetail != "") {
        document
          .querySelector(`#${this.startOrEndInDetail}_d`)
          .classList.remove("selectDate");
      }
      document.querySelector(`#${position}_d`).classList.add("selectDate");

      switch (position) {
        case "start":
          this.startOrEndInDetail = "start";
          break;
        case "end":
          this.startOrEndInDetail = "end";
          break;
      }
    },
    selectPriority(e) {
      this.inputPriority = e.target.value;
    },
    selectState(e) {
      this.inputState = e.target.value;
    },
    setColor(str) {
      switch (str) {
        case "낮음":
          return "#4caf50";
        case "보통":
          return "#0091ff";
        case "높음":
          return "#ffbf00";
        case "긴급":
          return "#ff6f00";
        case "즉시":
          return "#f44336";
      }
    },
    resetSelectedData() {
      this.inputProgress = "";
      this.inputPriority = "";
      this.inputState = "";
      this.inputStart = "";
      this.inputEnd = "";
      this.inputContent = "";
      this.inputTitle = "";
    },
  },
};
</script>

<style scoped>
.detail-container {
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
  box-shadow: 0px 3px 35px rgba(0, 0, 0, 0.5);
  border-radius: 15px;
  width: 210px;
  height: 230px;
  overflow: hidden;
  opacity: 1;
  z-index: 3;
}

.vuecal--date-picker ::-webkit-scrollbar {
  width: 0px;
}

.detail-header {
  display: flex;
  justify-content: space-between;
  width: 50%;
}

.detail-title {
  font-size: 18px;
}
.detail-line {
  width: 50%;
  margin: 0 0 6px 0;
}

.update-btn {
  background: #ff8906;
}

.delete-btn {
  background: #ff5c5c;
}

.btn {
  color: white;
  margin: 0 0 10px 10px;
  border-radius: 15px;
  width: 50px;
  padding: 2px;
  box-shadow: 3px 6px 10px rgba(255, 255, 255, 0.2) inset;
  filter: drop-shadow(0px 15px 15px rgba(10, 10, 10, 0.8));
  font-size: 12px;
}

.detail-table {
  border: none;
  width: 100%;
  text-align: left;
  height: 80%;
  margin: 2px;
  padding: 4px;
}

.detail-table th {
  width: 150px;
  filter: drop-shadow(2px 4px 4px rgba(10, 10, 10, 0.8));
}

.detail-table input {
  margin-top: 4px;
  background: none;
  border: none;
  outline: none;
  color: #fff;
  border-radius: 15px;
  font-size: 16px;
}

.detail-progress {
  filter: drop-shadow(2px 4px 4px rgba(10, 10, 10, 0.8));
  display: flex;
  width: 300px;
}

.detail-item-progress {
  width: 80%;
  height: 20px;
  max-width: 60%;
  background: #414556;
  border-radius: 15px;
  box-shadow: 3px 6px 10px rgba(255, 255, 255, 0.2) inset;
  margin-right: 10px;
}

.detail-item-progress-fill {
  height: 20px;
  width: 50%;
  background: #3f80a9;
  transition: all 1s ease;
  border-radius: 15px;
  z-index: 2;
  box-shadow: 3px 6px 10px rgba(255, 255, 255, 0.2) inset;
}

.detail-item-progress-percent input {
  width: 30%;
}

.selectBox {
  border: none;
  background: none;
  color: #fff;
  width: fit-content;
  outline: none;
  text-align: center;
  font-size: 16px;
}

.selectBox.state option {
  background: #414556;
}

.selectDate {
  color: #ff8906 !important;
}

.selectDate::placeholder {
  color: #ff8906;
}

.selectBox.priority option:nth-child(1) {
  background: #4caf50;
}
.selectBox.priority option:nth-child(2) {
  background: #0091ff;
}
.selectBox.priority option:nth-child(3) {
  background: #ffbf00;
}
.selectBox.priority option:nth-child(4) {
  background: #ff6f00;
}
.selectBox.priority option:nth-child(5) {
  background: #f44336;
}

@keyframes fade {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
</style>