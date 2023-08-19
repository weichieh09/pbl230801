<template>
  <b-container>
    <b-row>
      <b-col cols="12">
        <h3 style="text-align: center">下載</h3>
        <hr />
      </b-col>
      <b-col cols="12">
        <b-input-group size="lg" prepend="球隊">
          <b-form-select v-model="form.team" :options="form.teams" v-on:change="teamChange()" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="日期">
          <b-form-input type="date" v-model="form.date" v-on:change="dateChange()" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="場地">
          <b-form-select v-model="form.space" :options="form.spaces" v-on:change="spaceChange()" />
        </b-input-group>
        <br />
        <b-input-group size="lg" prepend="賽事">
          <b-form-select v-model="form.event" :options="form.events" v-on:change="eventChange()" />
        </b-input-group>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="6">
        <b-button block variant="outline-primary" size="lg" @click="getSbList()">戰績表 <b-icon icon="FileEarmarkText" /></b-button>
      </b-col>
      <b-col cols="6">
        <b-button block variant="outline-primary" size="lg" @click="getRtsList()">英雄榜 <b-icon icon="Award" /></b-button>
      </b-col>
    </b-row>
    <br />
    <b-row>
      <b-col cols="12">
        <xlsx-workbook>
          <xlsx-sheet :sheetName="xlsxData.name" :collection="xlsxData.data" />
          <xlsx-download :filename="getXlsxName()">
            <b-button block variant="success" size="lg" :disabled="xlsxData.data === null">Excel下載 <b-icon icon="download" /></b-button>
          </xlsx-download>
        </xlsx-workbook>
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

<script lang="ts" src="./wcc701.ts"></script>
