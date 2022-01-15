<template>
  <div class="gitCode">
    <div v-if="this.$store.state.git.selectedFileName === '' ">
      <h1>File Name</h1>
    </div>
    <div v-else-if="this.$store.state.git.selectedFileName !== '' ">
      <h1>{{this.$store.state.git.selectedFileName}}</h1>
    </div>


    <div class="fileContent">
      <div v-if="this.$store.state.git.selectedFileSize !== ''" >
        <h1 class="lineNum-fileSize">
          {{this.$store.state.git.maxLineNum}} {{this.$store.state.git.selectedFileSize}}
        </h1>
      </div>
      <prism-editor
          class="my-editor"
          v-model="this.$store.state.git.decodeData"
          :highlight="highlighter"
          line-numbers
          ignoreTabKey
          language="JavaScript"
          readonly
      >
      </prism-editor>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import { PrismEditor } from 'vue-prism-editor';
import 'vue-prism-editor/dist/prismeditor.min.css';
import { highlight, languages } from 'prismjs/components/prism-core';
import 'prismjs/components/prism-clike';
import 'prismjs/components/prism-javascript';
import 'prismjs/themes/prism-dark.css';

export default {
  components : {
    PrismEditor,
  },
  data(){
    return{
      s_decodeData : this.$store.state.git.decodeData,
    }
  },
  methods: {
    ...mapMutations({
      getDecodeData : 'git/getDecodeData',
      setMaxLineNum : 'git/setMaxLineNum',
    }),
    highlighter(code){
      return highlight(code, languages.js)
    },
    input_S_decodeData(){
      return this.s_decodeData = this.$store.state.git.decodeData
    },
    callMaxLineNum(){
      let line = document.querySelectorAll('.prism-editor__line-number.token.comment')
      this.setMaxLineNum(line.length)
    },
  },
  mounted() {
    this.input_S_decodeData()
  },
  updated(){
    this.callMaxLineNum()
  },
  watch : {
  },
}
</script>

<style scoped>

.gitCode {
  height: 100%;
}
.gitCode > div > h1{
  margin-bottom: 3px;
  color : #eee;
  font-size: 21px;
}
.fileContent{
  height: 92.5%;
  background-color: #2C2F3B;
  overflow: scroll;
  -ms-overflow-style: none;
  scrollbar-width: none;
  color: rgb(161, 177, 192);
  font-size: 22px;
}

.my-editor{
  font-family: Fira code, Fira Mono, Consolas, Menlo, Courier, monospace;
  font-size: 17px;
  line-height: 1.5;
  padding: 5px;
  height: 100vh;
  overflow: scroll;
}

.prism-editor__textarea:focus {
  outline: none;
}

.fileContent::-webkit-scrollbar, .my-editor::-webkit-scrollbar {
  display: none;
}

.ssh-pre[data-label],
.ssh-pre{
  margin-top: 0px;
}
.lineNum-fileSize{
  color : #eee;
  font-size : 16px;
  padding : 8px;
}
</style>