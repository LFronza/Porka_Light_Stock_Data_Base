import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGrupo } from 'app/shared/model/grupo.model';
import { GrupoService } from './grupo.service';

@Component({
    selector: 'jhi-grupo-delete-dialog',
    templateUrl: './grupo-delete-dialog.component.html'
})
export class GrupoDeleteDialogComponent {
    grupo: IGrupo;

    constructor(protected grupoService: GrupoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.grupoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'grupoListModification',
                content: 'Deleted an grupo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-grupo-delete-popup',
    template: ''
})
export class GrupoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ grupo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GrupoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.grupo = grupo;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/grupo', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/grupo', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
