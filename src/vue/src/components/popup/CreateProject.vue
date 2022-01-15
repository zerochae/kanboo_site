<template>
  <div>
    <div class="bg-container" @click="close">
      <div class="white-container">
        <button type="button" class="close-btn" @click="btnClose">X</button>
        <h1 class="createProject-title">Create Project</h1>
        <div class="createProject-content">
          <input
            v-model="inputName"
            class="create-input"
            type="text"
            placeholder="Name"
            id="name"
          />
          <input
            class="create-input"
            placeholder="Start"
            id="start"
            type="text"
            :value="inputStartDay"
            @click="showCal"
            readonly
          />
          <input
            class="create-input"
            id="end"
            type="text"
            placeholder="End"
            :value="inputEndDay"
            @click="showCal"
            readonly
          />
          <vue-cal
            locale="ko"
            class="vuecal--date-picker"
            xsmall
            hide-view-selector
            :time="false"
            :transitions="false"
            active-view="month"
            :disable-views="['years,week,days']"
            style="width: 210px; height: 230px"
            @cell-click="pickDate($event)"
            @dblclick="showCalendar = false"
            v-if="showCalendar"
          >
          </vue-cal>
        </div>
        <button @click="createProject()" class="createProject-btn">
          Create Project
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import VueCal from "vue-cal";
import "vue-cal/dist/vuecal.css";
import "vue-cal/dist/i18n/ko.js";
import moment from "moment";
import {mapMutations} from "vuex";
// import { CalendarIcon } from "@heroicons/vue/outline";

export default {
  data() {
    return {
      showCreateForm: true,
      inputName: "",
      pickDays: [],
      inputStartDay: "",
      inputEndDay: "",
      thisDay: "",
      showCalendar: false,
    };
  },
  components: {
    VueCal,
  },
  methods: {
    ...mapMutations({
      changeIsModalOpen: 'projectList/changeIsModalOpen',
      addCreatedProject: 'projectList/addCreatedProject'
    }),
    pickDate(data) {
      moment.locale("en")
      let today = moment().format("YYYY-MM-DD HH:mm");
      let todayWithOutTime = moment().format("YYYY-MM-DD");
      let selectDate = moment(data.format("YYYY-MM-DD"))._i;
      let temp = `${selectDate}`;

      selectDate = moment(temp, "YYYY-MM-DD");

      if (
        selectDate.from(today).split(" ")[0] !== "in" &&
        selectDate._i !== todayWithOutTime
      ) {
        let target = document.querySelector(
          ".vuecal__cell--selected .vuecal__cell-content"
        );
        target.style.background = "red";
        setTimeout(() => {
          target.style.background = "none";
        }, 1000);
        clearTimeout();
        return;
      } else {
        if (this.thisDay === "start") {
          this.inputStartDay = selectDate._i;
        } else {
          this.inputEndDay = selectDate._i;
        }
      }
    },
    showCal(e) {
      this.thisDay = e.target.id;
      this.showCalendar = true;
    },
    createProject() {
      if (this.inputName === "") {
        let target = document.getElementById("name");
        target.style.boxShadow = "0px 0px 40px 40px #dd323e";
        target.style.transition = "all 0.7s ease-in-out";
        setTimeout(() => {
          target.style.boxShadow = "none";
        }, 1000);
        clearTimeout();
        return;
      }

      let start = moment(this.inputStartDay, "YYYY-MM-DD");
      let end = moment(this.inputEndDay, "YYYY-MM-DD");

      if (
        start.from(end).split(" ")[0] === "in" ||
        this.inputStartDay === "" ||
        this.inputEndDay === ""
      ) {
        let target1 = document.getElementById("start");
        let target2 = document.getElementById("end");
        target1.style.boxShadow = "0px 0px 40px 40px #dd323e";
        target1.style.transition = "all 0.7s ease-in-out";
        target2.style.boxShadow = "0px 0px 40px 40px #dd323e";
        target2.style.transition = "all 0.7s ease-in-out";
        setTimeout(() => {
          target1.style.boxShadow = "none";
          target2.style.boxShadow = "none";
        }, 1000);
        clearTimeout();
        return;
      }


      const params = new URLSearchParams()
      params.append("prjctNm", this.inputName)
      params.append("prjctStartDate", this.inputStartDay)
      params.append("prjctEndDate", this.inputEndDay)
      params.append("token", sessionStorage.getItem("token"))

      this.axios
        .post("/pdtail/createProject", params)
        .then((result) => {
          console.log(result);
          this.changeIsModalOpen()
          this.addCreatedProject(result.data)
        })
        .catch((err) => {
          console.error(err);
        });


    },
    close(e) {
      if(e.target.classList[0] === "bg-container") {
        this.changeIsModalOpen()
      }
    },
    btnClose() {
      this.changeIsModalOpen()
    }
  },
};
</script>

<style scoped>
.callModal {
  font-size: 50px;
  background: cornflowerblue;
}

.close-btn {
  position: absolute;
  top: 30px;
  right: 30px;
  z-index: 99999;
  color: #fff;
  cursor: pointer;
}

.bg-container {
  z-index: 1;
  width: 100%;
  height: calc(100% - 70px);
  background: rgba(0, 0, 0, 0.36);
  position: absolute;
  color: #fff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.white-container {
  z-index: 2;
  width: 30vw;
  border-radius: 15px;
  background: #2c2f3b;
  padding: 25px;
  box-shadow: 0px 5px 25px rgba(0, 0, 0, 0.7);
  position: relative;
}

.createProject-content {
  display: flex;
  flex-direction: column;
  margin-bottom: 15px;
}

.createProject-title {
  font-size: 32px;
  margin-bottom: 35px;
}

.create-input {
  background: #414556;
  border: none;
  outline: none;
  margin-bottom: 15px;
  font-size: 22px;
  color: white;
  border-radius: 5px;
  padding: 5px 10px;
  box-shadow: 0px 5px 35px rgba(0, 0, 0, 0.5);
}

.createProject-btn {
  display: block;
  background: #ff8906;
  font-size: 24px;
  color: white;
  border-radius: 10px;
  margin: 0 auto;
  box-shadow: 0px 5px 35px rgba(0, 0, 0, 0.3);
}

.vuecal--date-picker {
  position: absolute;
  top: 0%;
  left: 100%;
  background: #2c2f3b;
  border-radius: 15px;
}

.icons {
  margin-top: 5px;
  width: 20px;
  vertical-align: sub;
  display: inline-block;
}
</style>