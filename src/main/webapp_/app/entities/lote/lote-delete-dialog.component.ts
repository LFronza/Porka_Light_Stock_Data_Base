import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILote } from 'app/shared/model/lote.model';
import { LoteService } from './lote.service';

@Component({
    selector: 'jhi-lote-delete-dialog',
    templateUrl: './lote-delete-dialog.component.html'
})
export class LoteDeleteDialogComponent {
    lote: ILote;

    constructor(protected loteService: LoteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.loteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'loteListModification',
                content: 'Deleted an lote'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lote-delete-popup',
    template: ''
})
export class LoteDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lote }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LoteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.lote = lote;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/lote', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/lote', { outlets: { popup: null } }]);
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
