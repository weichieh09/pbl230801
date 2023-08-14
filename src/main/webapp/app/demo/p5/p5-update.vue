<template>
  <b-container>
    <b-row>
      <b-col cols="12">
        <h3 style="text-align: center">球員管理</h3>
        <hr />
      </b-col>
    </b-row>
    <b-row v-if="form.pId != 0">
      <b-col cols="12">
        <b-input-group size="lg" prepend="序號">
          <b-form-input v-model="form.pId" disabled />
        </b-input-group>
        <br />
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="12">
        <b-input-group size="lg" prepend="等級">
          <b-form-input type="number" v-model="form.plyrLvl" />
        </b-input-group>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="12">
        <b-input-group size="lg" prepend="名稱">
          <b-form-input v-model="form.plyrNm" />
        </b-input-group>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="12" v-if="form.pId != 0">
        <b-button block variant="outline-primary" size="lg" @click="updatePlyr()">更新 <b-icon icon="cloudArrowUpFill" /></b-button>
      </b-col>
      <b-col cols="12" v-else>
        <b-button block variant="outline-primary" size="lg" @click="savePlyr()">新增 <b-icon icon="cloudArrowUpFill" /></b-button>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="12">
        <hr />
      </b-col>
      <b-col cols="12">
        <b-button block variant="outline-primary" size="lg" @click="$router.go(-1)">返回</b-button>
      </b-col>
    </b-row>
    <br />
  </b-container>
</template>

<script lang="ts" src="./p5-update.ts"></script>
