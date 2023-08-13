<template>
  <b-container>
    <b-row>
      <b-col cols="12">
        <h3 style="text-align: center">錯誤頁面！</h3>
        <hr />
      </b-col>
      <b-col cols="12" v-if="errorMessage">
        <div class="alert alert-danger">{{ errorMessage }}</div>
      </b-col>
      <b-col cols="12" v-if="error403">
        <div class="alert alert-danger"><b-icon icon="LockFill" /> 您無權訪問此頁面。</div>
      </b-col>
      <b-col cols="12" v-if="error404">
        <div class="alert alert-warning">您請求的頁面不存在。</div>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="12">
        <hr />
      </b-col>
      <b-col cols="12">
        <b-button block variant="outline-primary" size="lg" to="/">回首頁</b-button>
      </b-col>
    </b-row>
    <br />
  </b-container>
</template>

<script lang="ts" src="./error.component.ts"></script>
