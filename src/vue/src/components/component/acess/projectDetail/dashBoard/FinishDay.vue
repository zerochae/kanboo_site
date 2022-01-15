<template>
  <div class="container">
    Finish D-{{dDay}}
  </div>
</template>

<script>
export default {
  name: "finishDay",
  data() {
    return {
      dDay: 10
    }
  }
}
</script>

<style scoped>
.container {
  height: 70px;
  border-radius: 5px;
  background: #2C2F3B;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #ffffff;
  font-size: 32px;
}
</style>