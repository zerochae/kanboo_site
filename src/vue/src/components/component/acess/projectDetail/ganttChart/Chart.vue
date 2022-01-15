<template>
  <div class="chart-container"
       :style="{'max-height': maxHeight}">
    <div class="date-container">
      <div class="chart-header">
        <ChevronLeftIcon class="icons" @click="prevMonth" />
        <span class="monthName">{{ monthName() }}</span>
        <ChevronRightIcon class="icons" @click="nextMonth" />
      </div>
      <ul class="chart-date">
        <li v-for="day in this.$store.state.gantt.dateList" :key="day">
          {{ day }}
        </li>
      </ul>
    </div>
    <ul class="chart-bars">
      <li
        v-for="(task, index) in this.$store.state.gantt.showData"
        :key="index"
        @click="showInfo(index)"
      >
        <span
          :style="{
            background: setColor(task.priority),
            width: `${task.progress}%`,
          }"
        >
        </span>
        <img src="@/assets/con1.jpg" />
        <p>{{ task.title }}</p>
      </li>
    </ul>
    <div
      class="todayLine"
      :style="{
        left: `${todayLineOffsetLeft}px`,
      }"
      v-if="isToday"
    ></div>
  </div>
</template>

<script>
import moment from "moment";
import { mapActions, mapMutations } from "vuex";
import { ChevronLeftIcon, ChevronRightIcon } from "@heroicons/vue/outline";

export default {
  name: "chart",
  data() {
    return {
      year: "",
      month: "",
      isToday: "true",
      first: true,
      todayLineOffsetLeft: 0,
      todayLineOffsetTop: 0,
    };
  },
  props: {
    maxHeight: String
  },
  components: {
    ChevronLeftIcon,
    ChevronRightIcon,
  },
  created() {
    this.month = new Date().getMonth() + 1;
    this.renderDate();
    this.getUserInfo();
    this.getGanttData();
    window.addEventListener('resize', this.render )
  },
  mounted() {
    if (this.first) {
      this.renderTodayLine();
      this.renderChart();
    }
  },
  updated() {
    !this.first ? this.renderChart() : (this.first = false);
  },
  unmounted() {
    window.removeEventListener("resize", this.render)
  },
  methods: {
    ...mapMutations({
      select: "gantt/select",
      renderDate: "gantt/renderDate",
      renderChart: "gantt/renderChart",
      setPrevMonth: "gantt/setPrevMonth",
      setShowList: "gantt/setShowList",
      setNextMonth: "gantt/setNextMonth"
    }),
    ...mapActions({
      getGanttData: "gantt/getGanttData",
      getUserInfo: "gantt/getUserInfo"
    }),
    render(){
      this.renderTodayLine();
      this.renderChart();
    },
    renderTodayLine() {
      let today = moment().format("YYYY-MM-DD").split("-");

      if (today[1] == this.$store.state.gantt.month) {
        this.isToday = true;
      } else {
        this.isToday = false;
      }

      let days = document.querySelectorAll(".chart-date li");
      days = Array.from(days);

      let f_arr = days.filter((day) => day.textContent === today[2]);

      this.todayLineOffsetLeft = f_arr[0].offsetLeft + 50;
    },
    showInfo(index) {
      this.select(index);
    },
    prevMonth() {
      for (let key of Object.keys(this.$store.state.gantt.chart.tasks[this.$store.state.gantt.year])) {
        if (this.$store.state.gantt.month - 1 == key) {
          this.setPrevMonth()
          this.setShowList(this.$store.state.gantt.chart.tasks)
          this.renderDate();
          break;
        }
      }
      this.renderTodayLine();
    },
    nextMonth() {
      for (let key of Object.keys(this.$store.state.gantt.chart.tasks[this.$store.state.gantt.year])) {
        if (this.$store.state.gantt.month + 1 == key) {
          this.setNextMonth()
          this.setShowList(this.$store.state.gantt.chart.tasks)
          this.renderDate();
          break;
        }
      }
      this.renderTodayLine();
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
    monthName() {
      switch (this.$store.state.gantt.month) {
        case "1": case 1:
          return "January";
        case "2": case 2:
          return "February";
        case "3": case 3:
          return "March";
        case "4": case 4:
          return "April";
        case "5": case 5:
          return "May";
        case "6": case 6:
          return "June";
        case "7": case 7:
          return "July";
        case "8": case 8:
          return "August";
        case "9": case 9:
          return "September";
        case "10": case 10:
          return "October";
        case "11": case 11:
          return "November";
        case "12": case 12:
          return "December";
      }
    },
  },
};
</script>

<style scoped>
.chart-container {
  color: white;
  border-radius: 25px;
  background: #2c2f3b;
  height: 100%;
  padding: 20px;
  position: relative;
  overflow: hidden;
  width: 100%;
  box-shadow: 0px 10px 25px rgba(255, 255, 255, 0.2) inset;
  margin-bottom: 20px;
}

.chart-header {
  height: 60px;
  width: 100%;
  display: flex;
  justify-content: space-around;
}

.monthName {
  align-self: center;
  font-size: 26px;
}

.icons {
  width: 40px;
}

.date-container {
  position: absolute;
  width: 100%;
  left: 0;
  top: 5px;
  padding: 0 20px;
}

.chart-date {
  display: flex;
  margin: 10px 0 20px 0;
  font-weight: bold;
  font-size: 1.2rem;
  filter: drop-shadow(2px 4px 4px rgba(10, 10, 10, 0.8));
}

.chart-date li {
  flex: 1;
  min-width: 19px;
  text-align: center;
}

.chart-date li:not(:last-child) {
  position: relative;
}
.chart-date li:not(:last-child):before {
  position: absolute;
  content: "";
  right: 0;
  height: 100%;
  border-right: 1px solid #fff;
}

.chart-bars {
  position: relative;
  top: 20%;
  height: 85%;
  overflow: scroll;
  padding: 0 20px;
}

.chart-bars li {
  z-index: 2;
  position: relative;
  color: black;
  margin-bottom: 15px;
  font-size: 12px;
  border-radius: 20px;
  width: 0;
  opacity: 0;
  height: 50px;
  background: #eee;
  transition: all 0.6s linear 0.2s;
  overflow: hidden;
  display: flex;
  filter: drop-shadow(2px 4px 4px rgba(10, 10, 10, 0.8));
  font-weight: bold;
}

.chart-bars li img {
  border-radius: 50%;
  z-index: 1;
  padding: 5px;
}

.chart-bars li span {
  box-shadow: 3px 6px 25px rgba(255, 255, 255, 0.5) inset;
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  transition: all 0.6s linear 0.2s;
}

.chart-bars li p {
  position: absolute;
  top: 35%;
  margin-left: 50px;
  left: 0;
}

.todayLine {
  bottom: 0;
  position: absolute;
  height: 5%;
  border-right: 1px solid #ff000050;
  filter: drop-shadow(2px 4px 4px rgba(10, 10, 10, 0.8));
  z-index: 999999;
}

.todayLine::after {
  content: "Today";
  position: absolute;
  background: #ff8906;
  font-size: 12px;
  padding: 3px 5px;
  border-radius: 20px;
  color: #fff;
  top: -15px;
  left: -20px;
}

::-webkit-scrollbar {
  width: 0px;
}
</style>