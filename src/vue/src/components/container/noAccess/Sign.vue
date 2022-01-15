<template>
  <div class="sign-container">
    <Terminal />
  </div>
</template>

<script>
import Terminal from "@/components/component/noAccess/home/Terminal.vue";

export default {
  name: "Sign",
  components: {
    Terminal,
  },
};
</script>

<style scoped>
.sign-container{
  position: relative;
  width: 100%;
  height: calc(100vh - 70px);
  background: #16161a;
  overflow: hidden;
  color: white;
}
</style>