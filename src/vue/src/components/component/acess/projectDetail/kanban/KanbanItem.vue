<template>
  <Draggable v-if="item">
    <div class="kanban-card-container">
      <div v-if="item.loading" class="">
        <RefreshIcon class="icons spin" viewBox="0 0 24 24" />
      </div>
      <div class="card-space">
        <div class="kanban-card">
          <p>
            <span class="kanban-badge" :style="{ background: item.badgeColor }">
              {{ item.badgeText }}
            </span>
            <DotsHorizontalIcon
              @click="showMenu(columnIndex, cardIndex)"
              class="icons"
              style="float: right"
            />
          </p>
          <p>{{ item.user_name }}</p>
        </div>
        <transition name="showMenu">
          <div
            class="card-in-menu"
            v-if="item.showCardInMenu == true"
            :id="`cardMenu${columnIndex}${cardIndex}`"
          >
            <p @click="updateCard(columnIndex, cardIndex)">
              <PencilIcon class="icons" />
              수정
            </p>
            <hr/>
            <p @click="deleteCard(columnIndex, cardIndex)">
              <TrashIcon class="icons" /> 삭제
            </p>
          </div>
        </transition>
        <p>{{ item.content }}</p>
        <p class="kanban-timeZone">
          <ClockIcon class="icons" />
          {{ D_days(item.startDate, item.endDate, columnIndex, cardIndex) }}
        </p>
      </div>
    </div>
  </Draggable>
</template>

<script>
import { mapMutations, mapState } from "vuex";
import { Draggable } from "vue3-smooth-dnd";
import moment from "moment";
import {
  DotsHorizontalIcon,
  TrashIcon,
  PencilIcon,
  RefreshIcon,
  ClockIcon,
} from "@heroicons/vue/outline";
export default {
  name: "KanbanItem",
  components: {
    Draggable,
    DotsHorizontalIcon,
    TrashIcon,
    PencilIcon,
    RefreshIcon,
    ClockIcon,
  },
  computed: {
    ...mapState({
      nowOpen: (state) => state.kanban.nowOpen,
    }),
  },
  data() {
    return {
      day: "",
      time: "",
    };
  },
  props: {
    item: Object,
    columnIndex: Number,
    cardIndex: Number,
  },
  methods: {
    ...mapMutations({
      showCardMenu: "kanban/showCardMenu",
      delete: "kanban/delete",
      preUpdate: "kanban/preUpdate",
      setDays: "kanban/setDays",
      resetUpdateTarget: "kanban/resetUpdateTarget",
    }),
    showMenu(i, j) {
      var indexArr = [i, j];
      this.showCardMenu(indexArr);
    },
    deleteCard(i, j) {
      var indexArr = [i, j];
      this.delete(indexArr);
    },
    updateCard(i, j) {
      var indexArr = [i, j];
      this.preUpdate(indexArr);
    },
    D_days(start, end, i, j) {
      moment.locale("en");
      var today = moment().format("YYYY-MM-DD HH:mm:ss");
      var startDay = moment(start, "YYYY-MM-DD HH:mm:ss");
      var endDay = moment(end, "YYYY-MM-DD HH:mm:ss");

      var date = "";

      var registrationTime = startDay.from(today).split(" ");

      date +=
        registrationTime[0] === "a" || registrationTime[0] === "an"
          ? 1
          : registrationTime[0];

      switch (registrationTime[1]) {
        case "few":
          date = "now";
          break;
        case "minute":
        case "minutes":
          date += "분 전";
          break;
        case "hour":
        case "hours":
          date += "시간 전";
          break;
        case "day":
        case "days":
          date += "일 전";
          break;
        case "month":
        case "months":
          date += "달 전";
          break;
      }
      this.time = date;

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

      this.day = d_day;

      var payload = [];
      payload.push(`${this.time} (${this.day})`);
      payload.push(i);
      payload.push(j);
      this.setDays(payload);

      return `${this.time} (${this.day})`;
    },
  },
};
</script>

<style>
.card-space {
  background-color: #414556;
  margin: 15px;
  border-radius: 20px;
  padding: 15px;
  position: relative;
  box-shadow: 3px 6px 10px rgba(255, 255, 255, 0.2) inset;
}

.kanban-card {
  padding: 4px;
  width: 100%;
  height: 100%;
  display: inline;
  position: relative;
}
.card-in-menu {
  cursor: pointer;
  position: absolute;
  background-color: #2c2f3b;
  color: #fff;
  padding: 12px;
  border-radius: 10px;
  right: 0;
  top: 35%;
  box-shadow: 0 6px 30px rgba(0, 0, 0, 0.6);
}

.card-in-menu p:hover {
color: #ff8906;
}
.card-in-menu hr {
  margin-top: 12px;
}

.icons {
  margin-top: 5px;
  width: 20px;
  vertical-align: sub;
}

.icons:hover {
  color: #ff8906;
}

.showMenu-enter-from {
  opacity: 0;
}
.showMenu-enter-active {
  transition: all 1s;
}
.showMenu-enter-to {
  opacity: 1;
}

.showMenu-leave-from {
  opacity: 1;
}
.showMenu-leave-active {
  transition: all 0.5s;
}
.showMenu-leave-to {
  opacity: 0;
}
.kanban-badge {
  display: inline-block;
  font-weight: 700;
  border-radius: 0.25rem;
  padding: 0.15625rem 0.5rem;
  transform: translateY(-18%);
  font-size: 0.625rem;
  color: white;
  font-size: 15px;
  margin-bottom: 10px;
}

.kanban-timeZone {
  margin-top: 10px;
}

.drop-placeholder {
  border: 2px dotted #ff8906;
  border-radius: 50px;
}

.cardGhostDrag {
  transform: rotate(5deg) scale(1.1);
  transition: all 0.2s ease-in;
  border-radius: 50px;
  color: white;
}
.cardGhostDrop {
  transition: all 3s ease-in;
  transform: rotate(-20deg) scale(0.9);
}
</style>