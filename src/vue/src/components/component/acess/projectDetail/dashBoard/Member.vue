<template>
  <div class="container">
    <h2>Member</h2>
    <ul class="mem-list">
      <li v-for="item in this.$store.state.dashBoard.memberList"
          :key="item">
        <span class="icon"><img src="../../../../../assets/memIcon.png" alt="icon"></span>
        <span class="mem-role">{{item.prjctMemRole}}</span>
        <span class="mem-id">{{item.member.memId}}</span>
        <span class="mem-code">{{item.member.memTag}}</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "Member",
  data() {
    return {
      memList: [
        {
          memRole: "PL",
          memId: "kade87",
          memCode: "#8Ad9FX"
        },
        {
          memRole: "PL",
          memId: "kade87",
          memCode: "#8Ad9FX"
        },
        {
          memRole: "PL",
          memId: "kade87",
          memCode: "#8Ad9FX"
        },
        {
          memRole: "PL",
          memId: "kade87",
          memCode: "#8Ad9FX"
        },
      ]
    }
  }
}
</script>

<style scoped>
.container {
  height: 340px;
  width: 45%;
  border-radius: 5px;
  background: #2C2F3B;
  padding: 10px;
  color: #ffffff;
}

h2 {
  font-size: 32px;
  margin-bottom: 20px;
}

.mem-list {
  padding: 0 20px;
  font-size: 28px;
  height: calc(100% - 52px);
  overflow-y: scroll;
}

.mem-list li {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.mem-list li:last-child {
  margin-bottom: 0;
}
</style>