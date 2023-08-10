import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import { IVwEventResult, VwEventResult } from '@/shared/model/vw-event-result.model';
import VwEventResultService from './vw-event-result.service';

const validations: any = {
  vwEventResult: {
    eId: {},
    pId: {},
    mId: {},
    winFg: {},
    plyrNm: {},
    plyrLvl: {},
    mtchEndTime: {},
    totMatchs: {},
    totWins: {},
    acmlWins: {},
    chkFg: {},
  },
};

@Component({
  validations,
})
export default class VwEventResultUpdate extends Vue {
  @Inject('vwEventResultService') private vwEventResultService: () => VwEventResultService;
  @Inject('alertService') private alertService: () => AlertService;

  public vwEventResult: IVwEventResult = new VwEventResult();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.vwEventResultId) {
        vm.retrieveVwEventResult(to.params.vwEventResultId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.vwEventResult.id) {
      this.vwEventResultService()
        .update(this.vwEventResult)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A VwEventResult is updated with identifier ' + param.id;
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.vwEventResultService()
        .create(this.vwEventResult)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A VwEventResult is created with identifier ' + param.id;
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.vwEventResult[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.vwEventResult[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.vwEventResult[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.vwEventResult[field] = null;
    }
  }

  public retrieveVwEventResult(vwEventResultId): void {
    this.vwEventResultService()
      .find(vwEventResultId)
      .then(res => {
        res.mtchEndTime = new Date(res.mtchEndTime);
        this.vwEventResult = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
