import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import { IMatchZ, MatchZ } from '@/shared/model/match-z.model';
import MatchZService from './match-z.service';

const validations: any = {
  matchZ: {
    eId: {},
    mtchEndTime: {},
    wPlyr1: {},
    wPlyr2: {},
    wScr: {},
    lPlyr1: {},
    lPlyr2: {},
    lScr: {},
    lstMtnUsr: {},
    lstMtnDt: {},
  },
};

@Component({
  validations,
})
export default class MatchZUpdate extends Vue {
  @Inject('matchZService') private matchZService: () => MatchZService;
  @Inject('alertService') private alertService: () => AlertService;

  public matchZ: IMatchZ = new MatchZ();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.matchZId) {
        vm.retrieveMatchZ(to.params.matchZId);
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
    if (this.matchZ.id) {
      this.matchZService()
        .update(this.matchZ)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A MatchZ is updated with identifier ' + param.id;
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
      this.matchZService()
        .create(this.matchZ)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = 'A MatchZ is created with identifier ' + param.id;
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
      this.matchZ[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.matchZ[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.matchZ[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.matchZ[field] = null;
    }
  }

  public retrieveMatchZ(matchZId): void {
    this.matchZService()
      .find(matchZId)
      .then(res => {
        res.mtchEndTime = new Date(res.mtchEndTime);
        res.lstMtnDt = new Date(res.lstMtnDt);
        this.matchZ = res;
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
