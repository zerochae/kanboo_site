<template>
  <div class="query-container">
    <button class="close-query-btn"
            type="button"
            @click="closeExportQuery">X</button>
    <pre class="query">{{$store.state.erd.exportQuery}}</pre>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
export default {
  methods: {
    ...mapMutations({
      closeExportQuery: 'erd/closeExportQuery'
    })
  }
}
</script>

<style scoped>
@font-face {
  font-family: "NeoDunggeunmo";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.3/NeoDunggeunmo.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

* {
  font-family: "NeoDunggeunmo";
  font-size: 18px;
}

.query-container {
  position: absolute;
  border: 1px solid #999;
  background: #16161A;
  color: #00AB26;
  /* color: #fff; */
  bottom: 0;
  right: 0;
  width: 85%;
  height: 20vh;
}

.query {
  width: 100%;
  height: 100%;
  overflow: scroll;
  padding: 20px;
}

.close-query-btn {
  color: #fff;
  position: absolute;
  top: 10px;
  right: 10px;
  cursor: pointer;
}
</style>