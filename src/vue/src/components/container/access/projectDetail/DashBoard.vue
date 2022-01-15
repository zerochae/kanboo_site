<template>
  <div class="main-container">

    <section class="top">
      <Gantt  class="gantt"/>
      <div class="top-inner">
        <Prog  class="prog"/>
        <Issue  class="issue"/>
      </div>
    </section>

    <section class="middle">
      <div class="middle-top-inner">
        <Member class="member"/>
        <Commit class="commit"/>
      </div>
      <div class="middle-bottom-inner">
        <FinishDay class="finish-day"/>
        <Board class="board"/>
      </div>
    </section>

    <Schedule class="schedule"/>

    <Readme class="readme"/>

  </div>

</template>

<script>
import Gantt from '../../../component/acess/projectDetail/dashBoard/Gantt.vue'
import Prog from '../../../component/acess/projectDetail/dashBoard/Progress.vue'
import Issue from '../../../component/acess/projectDetail/dashBoard/Issue.vue'
import FinishDay from '../../../component/acess/projectDetail/dashBoard/FinishDay.vue'
import Board from '../../../component/acess/projectDetail/dashBoard/Board.vue'
import Member from '../../../component/acess/projectDetail/dashBoard/Member.vue'
import Commit from '../../../component/acess/projectDetail/dashBoard/Commit.vue'
import Schedule from '../../../component/acess/projectDetail/dashBoard/Schedule.vue'
import Readme from '../../../component/acess/projectDetail/dashBoard/Readme.vue'
import {mapActions} from "vuex";

export default {
  name: "DashBoard",
  data() {
    return {

    }
  },
  components: {
    Gantt,
    Prog,
    Issue,
    FinishDay,
    Board,
    Member,
    Commit,
    Schedule,
    Readme,
  },
  methods: {
    ...mapActions({
      mountGetData: 'dashBoard/mountGetData'
    })
  },
  mounted() {
    this.mountGetData()
  }
}
</script>

<style scoped>
.main-container {
  padding: 40px;
  display: flex;
  flex-direction: column;
}

.container > * {
  width: 100%;
}

.top {
  height: 510px;
  margin-bottom: 30px;
  display: flex;
}

.top-inner {
  display: flex;
  flex-direction: column;
}

.gantt {
  margin-right: 60px;
}

.middle {
  height: 340px;
  margin-bottom: 30px;
  display: flex;
}

.middle-top-inner {
  width: 70vw;
  display: flex;
  justify-content: space-between;
  margin-right: 60px;
}

.middle-bottom-inner {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 340px;
  width: 20vw;
}

.schedule {
  margin-bottom: 30px;
}

</style>