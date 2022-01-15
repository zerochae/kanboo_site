<template>
  <div class="container">
    <h2>Progress</h2>
    <div class="circle-box">
      <circle-progress :percent="now / total * 100"
                        :size="140"
                        fill-color="#3F80A9"
                        empty-color="#2C2F3B"/>
      <div class="count">
        <span class="now">{{now}}</span>
        <span class="seperator"> / </span>
        <span class="total">{{total}}</span>
      </div>
    </div>
  </div>
</template>

<script>
import "vue3-circle-progress/dist/circle-progress.css";
import CircleProgress from "vue3-circle-progress";

export default {
  name: "Progress",
  data() {
    return {
      now: 14,
      total: 40,
    }
  },
  components: {
    CircleProgress
  }
}
</script>

<style scoped>
.container {
  width: 20vw;
  height: 240px;
  margin-bottom: 30px;
  background: #2C2F3B;
  border-radius: 5px;
  padding: 15px 20px;
  font-size: 32px;
  color: #ffffff;
}

h2 {
  margin-bottom: 10px;
}

.circle-box {
  height: calc(100% - 52px);
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative; 
}

.count {
  position: absolute;
  transform: translate(-50%, -50%);
  top: 50%;
  left: 50%;
  font-size: 24px;
}

</style>