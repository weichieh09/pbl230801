<template>
  <b-container>
    <b-row>
      <b-col cols="12">
        <h3 style="text-align: center">賽事管理</h3>
        <hr />
      </b-col>
    </b-row>
    <b-row v-if="form.eId != 0">
      <b-col cols="12">
        <b-input-group size="lg" prepend="序號">
          <b-form-input v-model="form.eId" disabled />
        </b-input-group>
        <br />
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="12">
        <b-input-group size="lg" prepend="賽事">
          <b-form-input v-model="form.evntNm" />
        </b-input-group>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="12">
        <b-input-group size="lg" prepend="場地">
          <b-form-input v-model="form.venue" />
        </b-input-group>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="12">
        <b-input-group size="lg" prepend="球隊">
          <b-form-select v-model="form.tId" :options="teams" :disabled="form.eId != 0" />
        </b-input-group>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="12">
        <b-input-group size="lg" prepend="日期">
          <b-form-input type="date" v-model="form.evntDt" />
        </b-input-group>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="12">
        <b-input-group size="lg" prepend="開始">
          <b-form-input type="time" v-model="form.eventBegTime" />
        </b-input-group>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="12">
        <b-input-group size="lg" prepend="結束">
          <b-form-input type="time" v-model="form.eventEndTime" />
        </b-input-group>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="12" v-if="form.eId != 0">
        <b-button block variant="outline-primary" size="lg" @click="updateEventZ()">更新 <b-icon icon="cloudArrowUpFill" /></b-button>
      </b-col>
      <b-col cols="12" v-else>
        <b-button block variant="outline-primary" size="lg" @click="saveEventZ()">新增 <b-icon icon="cloudArrowUpFill" /></b-button>
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

<script lang="ts" src="./wcc601-update.ts"></script>
