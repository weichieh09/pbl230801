<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="prod">
        <h2 class="jh-entity-heading" data-cy="prodDetailsHeading"><span>Prod</span> {{ prod.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Prod No</span>
          </dt>
          <dd>
            <span>{{ prod.prodNo }}</span>
          </dd>
          <dt>
            <span>Ch Name</span>
          </dt>
          <dd>
            <span>{{ prod.chName }}</span>
          </dd>
          <dt>
            <span>Issu Dt</span>
          </dt>
          <dd>
            <span>{{ prod.issuDt }}</span>
          </dd>
          <dt>
            <span>Exp Dt</span>
          </dt>
          <dd>
            <span>{{ prod.expDt }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if="prod.id" :to="{ name: 'ProdEdit', params: { prodId: prod.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./prod-details.component.ts"></script>
