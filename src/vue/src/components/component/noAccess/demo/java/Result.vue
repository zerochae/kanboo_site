<template>
  <div class="result-container">
    <div class="title">Result</div>
    <div class="result-code" :style="{color: $store.state.demo.codeColor}">{{$store.state.demo.resultCode}}</div>
  </div>
</template>

<script>
export default {
  name: "Result",
}
</script>

<style scoped>
.result-container {
  padding: 10px;
}

.title {
  font-size: 20px;
  margin-bottom: 10px;
}

.result-code {
  overflow-y: scroll;
  text-overflow: clip;
  line-height: 1.25;
  height: calc(100% - 30px);
}
</style>