<template>
  <b-container>
    <b-row>
      <b-col cols="12">
        <h3 style="text-align: center">球隊管理</h3>
        <hr />
      </b-col>
    </b-row>
    <b-row v-if="form.tId != 0">
      <b-col cols="12">
        <b-input-group size="lg" prepend="序號">
          <b-form-input v-model="form.tId" disabled />
        </b-input-group>
        <br />
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="12">
        <b-input-group size="lg" prepend="名稱">
          <b-form-input v-model="form.tName" />
        </b-input-group>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="12" v-if="form.tId != 0">
        <b-button block variant="outline-primary" size="lg" @click="updateTeam()">更新 <b-icon icon="cloudArrowUpFill" /></b-button>
      </b-col>
      <b-col cols="12" v-else>
        <b-button block variant="outline-primary" size="lg" @click="saveTeam()">新增 <b-icon icon="cloudArrowUpFill" /></b-button>
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

<script lang="ts" src="./p4-update.ts"></script>
