package com.mobilegame.robozzle.utils.infixStyle

import com.mobilegame.robozzle.domain.RobuzzleLevel.Position

infix fun Position.match(pos: Position) = this.Match(pos)