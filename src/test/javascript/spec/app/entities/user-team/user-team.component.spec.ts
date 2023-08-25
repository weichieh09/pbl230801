/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import UserTeamComponent from '@/entities/user-team/user-team.vue';
import UserTeamClass from '@/entities/user-team/user-team.component';
import UserTeamService from '@/entities/user-team/user-team.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('UserTeam Management Component', () => {
    let wrapper: Wrapper<UserTeamClass>;
    let comp: UserTeamClass;
    let userTeamServiceStub: SinonStubbedInstance<UserTeamService>;

    beforeEach(() => {
      userTeamServiceStub = sinon.createStubInstance<UserTeamService>(UserTeamService);
      userTeamServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<UserTeamClass>(UserTeamComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          userTeamService: () => userTeamServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      userTeamServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllUserTeams();
      await comp.$nextTick();

      // THEN
      expect(userTeamServiceStub.retrieve.called).toBeTruthy();
      expect(comp.userTeams[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      userTeamServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(userTeamServiceStub.retrieve.called).toBeTruthy();
      expect(comp.userTeams[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      userTeamServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(userTeamServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      userTeamServiceStub.retrieve.reset();
      userTeamServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(userTeamServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.userTeams[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      userTeamServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(userTeamServiceStub.retrieve.callCount).toEqual(1);

      comp.removeUserTeam();
      await comp.$nextTick();

      // THEN
      expect(userTeamServiceStub.delete.called).toBeTruthy();
      expect(userTeamServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
