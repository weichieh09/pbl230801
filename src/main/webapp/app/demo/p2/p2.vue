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
      <br />
      <b-col cols="12" class="d-flex justify-content-center">
        <h3 class="text-success">勝方</h3>
      </b-col>
      <b-col cols="4" class="d-flex justify-content-center">
        <b-button block variant="outline-success" size="lg" @click="showModal('win1')">選手1</b-button>
      </b-col>
      <b-col cols="4" class="d-flex justify-content-center">
        <b-form-input size="lg" style="border-color: #28a745" />
      </b-col>
      <b-col cols="4" class="d-flex justify-content-center">
        <b-button block variant="outline-success" size="lg" @click="showModal('win2')">選手2</b-button>
      </b-col>
      <br />
    </b-row>
    <br />
    <b-row>
      <br />
      <b-col cols="12" class="d-flex justify-content-center">
        <h3 class="text-danger">敗方</h3>
      </b-col>
      <b-col cols="4" class="d-flex justify-content-center">
        <b-button block variant="outline-danger" size="lg" @click="showModal('lose1')">選手1</b-button>
      </b-col>
      <b-col cols="4" class="d-flex justify-content-center">
        <b-form-input size="lg" style="border-color: #dc3545" />
      </b-col>
      <b-col cols="4" class="d-flex justify-content-center">
        <b-button block variant="outline-danger" size="lg" @click="showModal('lose2')">選手2</b-button>
      </b-col>
      <br />
    </b-row>

    <br /><br />

    <b-row>
      <b-col cols="12" class="d-flex justify-content-center">
        <b-button block variant="outline-primary" size="lg" to="/demo/p1">賽後登錄</b-button>
      </b-col>
    </b-row>

    <!-- The modal -->
    <b-row>
      <b-col cols="12">
        <b-modal ref="my-modal" hide-footer title="選擇選手">
          <b-row>
            <b-col cols="9">
              <b-form-input v-model="searchName" placeholder="姓名" size="lg" />
            </b-col>
            <b-col cols="3">
              <b-button block variant="outline-primary" size="lg">
                <b-icon icon="search" />
              </b-button>
            </b-col>
          </b-row>
          <br />
          <b-row>
            <b-col cols="12">
              <b-list-group v-for="item in items" :key="item.id">
                <b-list-group-item button @click="hideModal()">
                  <h5>{{ item.plyrNm }}</h5>
                </b-list-group-item>
              </b-list-group>
            </b-col>
          </b-row>
          <br />
          <b-row>
            <b-col cols="12" class="d-flex justify-content-center">
              <b-pagination v-model="currentPage" :total-rows="rows" :per-page="perPage" aria-controls="my-table" />
            </b-col>
          </b-row>
        </b-modal>
      </b-col>
    </b-row>

    <br /><br />
  </b-container>
</template>

<script lang="ts" src="./p2.ts"></script>
