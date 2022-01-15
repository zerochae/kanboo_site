<template>
  <div class="editor-container">
    <Codemirror v-model:value="this.$store.state.htmlCompile.detail"
                :options="cmOptions"
                class="code-container"
                @input="updateHtml"/>
    <v-md-preview-html :v-model="this.$store.state.htmlCompile.detail"
                        preview-class="vuepress-markdown-body" 
                        :html="this.$store.state.htmlCompile.detail"></v-md-preview-html>

  </div>
</template>

<script>
import Codemirror from "codemirror-editor-vue3";
import "codemirror-editor-vue3/dist/style.css"
import "codemirror/theme/dracula.css"
import "codemirror/mode/htmlmixed/htmlmixed.js";
import {mapActions} from "vuex";

export default {
  components: { Codemirror },
  data() {
    return {
      cmOptions: {
        mode: 'text/html',
        theme: 'dracula',
        lineNumbers: true,
        smartIndent: true,
        indentUnit: 2,
        foldGutter: true,
        styleActiveLine: true,
      }
    }
  },
  methods: {
    ...mapActions({
      updateHtml: "htmlCompile/updateHtml"
    })
  }
}
</script>

<style scoped>
.editor-container {
  width: 100%;
  display: flex;
}

.editor-container > * {
  width: 50%;
}

textarea {
  color: #000;
  resize: none;
}

.code-container {
  border: 1px solid #999;
  font-size: 20px;
}
</style>