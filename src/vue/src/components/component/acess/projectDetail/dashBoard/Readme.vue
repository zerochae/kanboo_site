<template>
  <div class="container">
    <h2>README.md</h2>
    <v-md-editor v-model="this.$store.state.dashBoard.readMeContent"
                 @change="updateReadMe"
                  height="480px"/>
  </div>
</template>

<script>
import {mapActions} from "vuex";

export default {
  name: "readme",
  data() {
    return {
    }
  },
  methods: {
    ...mapActions({
      saveReadMe: 'dashBoard/saveReadMe'
    }),
    updateReadMe(origin) {
      this.saveReadMe(origin)
    }
  },
}
</script>

<style scoped>
.container {
  border-radius: 5px;
  background: #2C2F3B;
  padding: 20px;
  color: #ffffff;
}

h2 {
  font-size: 32px;
  margin-bottom: 10px;
}

h2 span {
  font-size: 24px;
}

</style>