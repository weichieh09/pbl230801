<template>
  <b-container fluid>
    <b-row>
      <b-col cols="12">
        <br />
        <b-input-group size="lg" prepend="球隊">
          <b-form-select v-model="form.team" :options="teams" v-on:change="teamChange()" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="日期">
          <b-form-input type="date" v-model="form.date" v-on:change="dateChange()" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="場地">
          <b-form-select v-model="form.space" :options="spaces" v-on:change="spaceChange()" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="賽事">
          <b-form-input v-model="form.event" disabled />
        </b-input-group>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="6" class="d-flex justify-content-center">
        <!-- <b-button variant="outline-primary" size="lg" @click="openLogin()">管理者登入</b-button> -->
        <b-button variant="outline-primary" size="lg" to="/demo/p3">管理者登入</b-button>
      </b-col>
      <b-col cols="6" class="d-flex justify-content-center">
        <b-button variant="outline-primary" size="lg" to="/demo/p2">球友登入</b-button>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="12" class="d-flex justify-content-center">
        <h5>即時戰績排行榜</h5>
      </b-col>
      <b-col cols="12">
        <b-table striped hover :items="items" />
      </b-col>
    </b-row>
  </b-container>
</template>

<script lang="ts" src="./p1.ts"></script>
