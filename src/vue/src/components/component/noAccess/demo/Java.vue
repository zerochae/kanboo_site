<template>
  <div class="java-container">
    <Editor/>
    <Result/>
  </div>
</template>

<script>
import Editor from '@/components/component/noAccess/demo/java/Editor.vue'
import Result from '@/components/component/noAccess/demo/java/Result.vue'

export default {
  name: "javaCompiler",
  components: {
    Editor,
    Result
  }
}
</script>

<style scoped>
.java-container {
  width: 85%;
  display: flex;
  justify-content: space-between;
}

.java-container > * {
  width: 50%;
  border: 1px solid #999;
}
</style>