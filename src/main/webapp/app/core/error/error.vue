<template>
  <div>
    <div class="row">
      <div class="col-md-3">
        <!-- <span class="hipster img-fluid rounded"></span> -->
      </div>
      <div class="col-md-9">
        <h1>錯誤頁面！</h1>

        <div v-if="errorMessage">
          <div class="alert alert-danger">{{ errorMessage }}</div>
        </div>
        <div v-if="error403" class="alert alert-danger">您無權訪問此頁面。</div>
        <div v-if="error404" class="alert alert-warning">您請求的頁面不存在。</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./error.component.ts"></script>
