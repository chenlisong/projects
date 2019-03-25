package com.duolanjian.design.mediator;

public abstract class AbstractColleague {

	protected AbstractMediator mediator;
	
	public AbstractColleague(AbstractMediator _mediator) {
		this.mediator = _mediator;
	}
	
}
