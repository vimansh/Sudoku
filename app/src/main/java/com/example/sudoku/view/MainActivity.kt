package com.example.sudoku.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.sudoku.R
import com.example.sudoku.game.Cell
import com.example.sudoku.view.custom.SudokuBoardView
import com.example.sudoku.viewmodel.PlaySudokuViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.LineNumberInputStream

class MainActivity : AppCompatActivity(), SudokuBoardView.OnTouchListener {

    private  lateinit var viewModel: PlaySudokuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sudokuBoardView.registerListener(this)

        viewModel =ViewModelProviders.of(this).get(PlaySudokuViewModel::class.java)
        viewModel.sudokuGame.selectedCellLiveData.observe(this, Observer { updateSelectedCellUI(it) })
        viewModel.sudokuGame.cellsLiveData.observe(this, Observer { updateCells(it) })

        val buttons = listOf(onebutton,twobutton,threebutton,fourbutton,fivebutton,sixbutton,
            sevenbutton,eightbutton,ninebutton)

        buttons.forEachIndexed{ index, button ->
            button.setOnClickListener {
                        viewModel.sudokuGame.handleInput( index+1)

            }
        }
    }

    private fun updateCells(cells: List<Cell>?) = cells?.let {
        sudokuBoardView.updateCells(cells)
    }

    private fun updateSelectedCellUI(cell : Pair<Int, Int>?) = cell?.let {
        sudokuBoardView.updateSelectedCellUI(cell.first, cell.second)
    }

    override fun OnCellTouched(row: Int,col: Int){
        viewModel.sudokuGame.updateSelectedCell(row,col)
    }
}
