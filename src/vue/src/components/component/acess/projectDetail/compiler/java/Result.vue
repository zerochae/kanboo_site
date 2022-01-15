<template>
  <div class="result-container">
    <div class="result" v-text="result" :style="{color: isError}"></div>
  </div>
</template>

<script>
import {mapState} from "vuex";

export default {
  name: "Result",
  computed: {
    ...mapState({
      isError: state => state.javaCompile.isError
    })
  },
  data() {
    return {
      result: ""
    }
  },
  watch: {
    "$store.state.javaCompile.result"(e) {
      this.result = e
    }
  }
}
</script>

<style scoped>
.result-container {
  padding: 10px;
}
</style>