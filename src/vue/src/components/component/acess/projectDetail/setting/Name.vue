<template>
  <div className="project-name-div">
    <div className="text-div">{{ $store.state.setting.projectName }}</div>

  </div>
</template>

<script>
export default {}
</script>

<style scoped>
.project-name-div {
  border-radius: 5px;
  display: flex;
  justify-content: flex-start;
  padding: 15px;
  width: 60vw;
  height: 60px;
  margin-top: 50px;
  margin-bottom: auto;
  box-sizing: border-box;
  background-color: #2C2F3B;
  color: white;
}

</style>